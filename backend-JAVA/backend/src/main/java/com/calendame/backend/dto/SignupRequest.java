package com.calendame.backend.dto;

public class SignupRequest {
    private String username;
    private String email;
    private String password;

    // ========== Getter 和 Setter 方法 ==========
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
