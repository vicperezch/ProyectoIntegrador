package com.proyectointegrador.controller;

import com.proyectointegrador.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }
}
