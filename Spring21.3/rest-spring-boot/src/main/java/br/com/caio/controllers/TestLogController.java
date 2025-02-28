package br.com.caio.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
public class TestLogController {


    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/test")
    public String testLog() {
        logger.debug("Testing log DEBUG");
        logger.info("Testing log INFO");
        logger.warn("Testing log WARN");
        logger.error("Testing log ERROR");

        return "Logs generated sucessfully!";
    }
}
