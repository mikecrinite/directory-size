package com.crinite.directorysize.service;

import com.crinite.directorysize.domain.Directory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
public class DirectoryCache {
    private final Integer maxCacheSize;
    private final Integer cacheTTL;

    private Cache<String, Directory> directoryCache;

    public DirectoryCache(Integer maxCacheSize, Integer cacheTTL) {
        this.maxCacheSize = maxCacheSize;
        this.cacheTTL = cacheTTL;
        init();
    }

    public void store(Directory directory) {
        if (directory == null) {
            return;
        }

        String path = directory.getPath();
        if (isNotBlank(path)) {
            directoryCache.put(path, directory);
        }
    }

    public Directory get(String path) {
        return directoryCache.getIfPresent(path);
    }

    public Collection<Directory> getAll() {
        return directoryCache.asMap().values();
    }

    private void init() {
        this.directoryCache = CacheBuilder.newBuilder()
                .maximumSize(maxCacheSize)
                .expireAfterWrite(cacheTTL, TimeUnit.HOURS)
                .build();
    }
}
