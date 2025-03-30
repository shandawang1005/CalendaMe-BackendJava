package com.calendame.backend.dto;
//used to collect data from frontend (request) and this class to secure data
public class LoginRequest {

    private String email;
    private String password;

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String password() {
        return password;

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
