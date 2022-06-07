package com.gba.people.manager.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Source Controller")
@RestController
@RequestMapping(value = "/source")
public class SourceController {

    private static final String GIT_URL = "https://github.com/gbagabriel5/people-manager";

    @GetMapping
    public String source() {
        return GIT_URL;
    }
}
