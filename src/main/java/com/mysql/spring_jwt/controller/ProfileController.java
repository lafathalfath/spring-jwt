package com.mysql.spring_jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.model.UserProfile;
import com.mysql.spring_jwt.service.table.ProfileService;

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

    @PostMapping("/store")
    public ResponseEntity<String> storeProfile(@RequestHeader("Authorization") String token, @RequestBody UserProfile request) {
        profileService.storeProfile(token, request);
        return ResponseEntity.status(201)
            .body("Store profile successful");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestHeader("aAuthorization") String token, @RequestBody UserProfile request) {
        profileService.updateProfile(token, request);
        return ResponseEntity.status(200)
            .body("Update profile successful");
    }

}
