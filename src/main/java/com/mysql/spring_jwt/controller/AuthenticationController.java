package com.mysql.spring_jwt.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.response.AuthenticationResponse;
import com.mysql.spring_jwt.service.auth.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh() {
        return ResponseEntity
            .status(200)
            .body(authenticationService.refreshAuthentication());
    }

    @GetMapping("/get-expiration") 
    public ResponseEntity<?> getExpiration() {
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("isExpire",authenticationService.getExpiration());
        return ResponseEntity.status(200)
            .body(response);
    }

    @GetMapping("/get-user-data")
    public ResponseEntity<?> getUserData() {
        return ResponseEntity.status(200)
            .body(authenticationService.getUserData());
    }

}
