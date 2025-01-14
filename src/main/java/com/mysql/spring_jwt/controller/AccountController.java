package com.mysql.spring_jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.model.UserProfile;
import com.mysql.spring_jwt.service.table.AccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    
    private final AccountService accountService;

    // public AccountController(AccountService accountService) {
    //     this.accountService = accountService;
    // }

    @GetMapping
    public ResponseEntity<?> getProfile() {
        return ResponseEntity
            .status(200)
            .body(accountService.getProfile());
    }

    @PostMapping("/profile/store")
    public ResponseEntity<String> storeProfile(@RequestBody UserProfile request) {
        accountService.storeProfile(request);
        return ResponseEntity
            .status(201)
            .body("Store profile successful");
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfile request) {
        return ResponseEntity
            .status(200)
            .body(accountService.updateProfile(request));
    }

    @PostMapping("/tes")
    public ResponseEntity<?> tes(
        @RequestBody User user
    ) {
        return ResponseEntity
            .status(200)
            .body(accountService.tes(user));
    }

}
