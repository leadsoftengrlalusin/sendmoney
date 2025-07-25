package com.gxchange.sendmoney.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Value("${profile.greeting}")
    private String greeting;

    @GetMapping
    public String getProfileGreeting() {
        return greeting;
    }
}
