package com.mysql.spring_jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) return ResponseEntity.status(403).build();
        User profile = profileService.getProfile(token);
        return ResponseEntity.ok(profile);
    }

}
