package com.numericaideas.multipleauthstrategies.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public String home() {
        return "This is the response for the basic api call";
    }

    /**
     * You should add a request body parameter in this method
     * and implement the way you want to generate jwt token
     *
     * @return The jwt token
     */
    @PostMapping("/auth")
    public String authentication() {
        return "The jwt token";
    }
}
