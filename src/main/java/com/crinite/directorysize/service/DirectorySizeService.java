package com.crinite.directorysize.service;

import com.crinite.directorysize.domain.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
@Component
public class DirectorySizeService {
    private static final Logger logger = LoggerFactory.getLogger(DirectorySizeService.class);

    @Value("${directorysize.logEveryFile}")
    private boolean logEveryFile = true;

    @Value("${directorysize.cacheEveryFolder}")
    private boolean cacheEveryDirectory = true;

    @Value("${directorysize.sortChildren}")
    private boolean sortChildren = false;

    @Resource
    private DirectoryCache directoryCache;

    public Directory determineDirectorySize(String path, boolean forceUpdate) {
        logger.info("Determining directory info for path: {}", path);
        File javaDirectory = new File(path);

        if (!javaDirectory.exists()) {
            logger.info("No directory exists at path " + path);
            return null;
        } else if (!javaDirectory.isDirectory()) {
            logger.info("File at path '{}' is not a directory", path);
            return null;
        }

        Directory directory = directoryCache.get(path);
        if (!forceUpdate && directory != null) {
            logger.info("Found directory with path '{}' in the directoryCache", directory.getPath());
            return directory;
        }

        directory = setUpDirectory(javaDirectory);

        recursivelyParseDirectories(directory);

        directoryCache.store(directory);
        return directory;
    }

    private void recursivelyParseDirectories(Directory directory) {
        long size = folderSize(directory);

        logger.info("Final size for directory '{}': {} ({} bytes)", directory.getName(), directory.getDisplaySize(), size);
        directory.setSize(size);
    }

    private long folderSize(Directory target) {
        if (logEveryFile) logger.info("Entering new folder: {}", target.getName());
        File directory = target.getJavaDirectory();
        File[] children = directory.listFiles();

        long size = 0;

        if (children != null && children.length > 0) {
            for (File child : children) {
                if (child.isFile()) {
                    if (logEveryFile) logger.info("\tFile '{}' has size: {} bytes", child.getName(), size);
                    size += child.length();
                } else {
                    Directory curr = setUpDirectory(child);
                    long currSize = folderSize(curr);
                    target.getChildren().add(curr);

                    curr.setParent(target);
                    curr.setSize(currSize);

                    if (cacheEveryDirectory) {
                        directoryCache.store(curr);
                    }

                    size += currSize;
                }
            }
        }

        if (sortChildren) {
            target.sortChildren();
        }

        return size;
    }

    private Directory setUpDirectory(File javaDirectory) {
        Directory directory = new Directory();

        directory.setJavaDirectory(javaDirectory);
        directory.setName(javaDirectory.getName());
        directory.setPath(javaDirectory.getPath());
        directory.setAbsolutePath(javaDirectory.getAbsolutePath());

        return directory;
    }

    public DirectoryCache getDirectoryCache() {
        return directoryCache;
    }

    public void setDirectoryCache(DirectoryCache directoryCache) {
        this.directoryCache = directoryCache;
    }
}
