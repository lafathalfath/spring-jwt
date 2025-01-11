package com.mysql.spring_jwt.service;

import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.UserRepository;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ProfileService(
        UserRepository userRepository,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User getProfile(String token) {
        Integer userId = jwtService.extractUserId(token);
        return userRepository.findById(userId)
            .orElseThrow();
    }

}
