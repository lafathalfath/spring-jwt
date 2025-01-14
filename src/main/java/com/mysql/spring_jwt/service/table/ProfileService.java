package com.mysql.spring_jwt.service.table;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.model.UserProfile;
import com.mysql.spring_jwt.repository.UserProfileRepository;
import com.mysql.spring_jwt.repository.UserRepository;
import com.mysql.spring_jwt.service.auth.JwtService;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    protected final UserProfileRepository userProfileRepository;
    private final JwtService jwtService;

    public ProfileService(
        UserRepository userRepository,
        UserProfileRepository userProfileRepository,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.jwtService = jwtService;
    }

    public User getProfile(String token) {
        Integer userId = jwtService.extractUserId(token);
        return userRepository.findById(userId)
            .orElseThrow();
    }

    public UserProfile storeProfile(String token, UserProfile profile) {
        Integer userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId)
            .orElseThrow();
        profile.setUser(user);
        profile.setAddress(profile.getAddress());
        profile.setPhone(profile.getPhone());
        return userProfileRepository.save(profile);
    }

    public UserProfile updateProfile(String token, UserProfile request) {
        Integer userId = jwtService.extractUserId(token);
        UserProfile profile = userProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        profile.setAddress(request.getAddress());
        profile.setPhone(request.getPhone());
        return userProfileRepository.save(profile);
    }

}
