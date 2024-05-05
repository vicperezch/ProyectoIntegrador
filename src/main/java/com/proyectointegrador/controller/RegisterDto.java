package com.proyectointegrador.controller;

public class RegisterDto {
    private final String email;
    private final String password;
    private final String name;

    public RegisterDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
