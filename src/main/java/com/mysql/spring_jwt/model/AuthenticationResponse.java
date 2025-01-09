package com.mysql.spring_jwt.model;

public class AuthenticationResponse {
    
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
