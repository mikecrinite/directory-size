package com.crinite.directorysize.controller;

import com.crinite.directorysize.domain.Directory;
import com.crinite.directorysize.service.DirectorySizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
@Controller
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final String VIEW_NAME = "index";

    @Resource
    private DirectorySizeService directorySizeService;

    @PostMapping("/directory")
    public String directorySizeForPath(@RequestParam("path") String path, Model model) {
        logger.info("Determining directory size for path '{}' and children", path);
        model.addAttribute("lastPath", path);

        if (isNotBlank(path)) {
            Directory directory = directorySizeService.determineDirectorySize(path, false);
            if (directory != null) {
                model.addAttribute("directory", directory);
                return VIEW_NAME;
            }
        }

        return VIEW_NAME;
    }

    @GetMapping("/directory")
    public String directory(@RequestParam("path") String path, Model model) {
        return directorySizeForPath(path, model);
    }
}
