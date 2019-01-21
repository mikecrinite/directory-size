package com.crinite.directorysize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
@SpringBootApplication
public class DirectorySize {
    protected static Logger logger = LoggerFactory.getLogger(DirectorySize.class);

    public static void main(String[] args) {
        SpringApplication.run(DirectorySize.class, args);
    }

    @Component
    public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(final ApplicationReadyEvent event) {
            String path = "http://localhost:8080";

            if (Desktop.isDesktopSupported()) {
                logger.info("Desktop is supported");
                Desktop desktop = Desktop.getDesktop();
                try {
                    logger.info("Attempting to browse to {}", path);
                    URI uri = new URI(path);
                    desktop.browse(uri);
                } catch (IOException | URISyntaxException e) {
                    logger.info("Navigate to {} in your browser", path);
                }
            } else {
                logger.info("Navigate to {} in your browser", path);
            }
        }

    }
}
