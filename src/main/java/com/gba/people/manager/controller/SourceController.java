package com.gba.people.manager.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Source Controller")
@RestController
@RequestMapping(value = "/source")
public class SourceController {

    @Value("${git.url}")
    private String gitUrl;

    @GetMapping
    public String source() {
        return gitUrl;
    }
}
