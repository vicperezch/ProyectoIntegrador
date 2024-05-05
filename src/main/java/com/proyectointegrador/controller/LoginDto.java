package com.proyectointegrador.controller;

public class LoginDto {

    private final String email;

    private final String password;


    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
