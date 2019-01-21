package com.crinite.directorysize.service;

import com.crinite.directorysize.domain.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.testng.Assert.*;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
public class DirectorySizeServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(DirectorySizeServiceTest.class);
    private DirectorySizeService directorySizeService;

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        directorySizeService = new DirectorySizeService();
        directorySizeService.setDirectoryCache(new DirectoryCache(100, 1));
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        directorySizeService.setDirectoryCache(null);
        directorySizeService = null;
    }

    @org.testng.annotations.Test
    public void testDetermineDirectorySize() {
        directorySizeService.determineDirectorySize("/Users/michaelcrinite/Documents/projects/github/mikecrinite", true);
        DirectoryCache directoryCache = directorySizeService.getDirectoryCache();
        Collection<Directory> all = directoryCache.getAll();

        for(Directory directory : all){
            logger.info(directory.getPath() + " : " + directory.getSize() + " bytes");
        }
    }
}