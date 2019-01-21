package com.crinite.directorysize.config;

import com.crinite.directorysize.service.DirectoryCache;
import com.crinite.directorysize.service.DirectorySizeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
@Configuration
public class ServicesConfig {
    @Value("${directorysize.maxCacheSize}")
    private Integer maxCacheSize;

    @Value("${directorysize.cacheTTL.hours}")
    private Integer cacheTTL;

    @Bean
    public DirectoryCache directoryCache(){
        return new DirectoryCache(maxCacheSize, cacheTTL);
    }

    @Bean
    public DirectorySizeService directorySizeService(){
        return new DirectorySizeService();
    }
}
