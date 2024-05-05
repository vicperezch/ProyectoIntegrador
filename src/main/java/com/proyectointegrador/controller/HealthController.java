package com.proyectointegrador.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/api/v1/health")
    public String checkAPI() {
        return "<h1>The API is working ok!<h1>";
    }
}
