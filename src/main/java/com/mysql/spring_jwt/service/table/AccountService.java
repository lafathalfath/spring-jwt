package com.mysql.spring_jwt.service.table;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mysql.spring_jwt.dto.ResponseDto;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.model.UserProfile;
import com.mysql.spring_jwt.repository.UserProfileRepository;
import com.mysql.spring_jwt.repository.UserRepository;
import com.mysql.spring_jwt.service.auth.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AccountService {

    private final UserRepository userRepository;
    protected final UserProfileRepository userProfileRepository;
    private final JwtService jwtService;

    private final HttpServletRequest servletRequest;

    public AccountService(
        UserRepository userRepository,
        UserProfileRepository userProfileRepository,
        JwtService jwtService,
        HttpServletRequest servletRequest
    ) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.jwtService = jwtService;
        this.servletRequest = servletRequest;
    }

    public ResponseDto<User> getProfile() {
        Integer userId = jwtService.extractUserId(servletRequest.getHeader("Authorization"));
        User user =  userRepository.findById(userId)
            .orElseThrow();
        return new ResponseDto<User>(
            "OK", 
            "get user data successfully", 
            user
        );
    }

    public ResponseDto<UserProfile> storeProfile(UserProfile profile) {
        Integer userId = jwtService.extractUserId(servletRequest.getHeader("Authorization"));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        profile.setUser(user);
        profile.setAddress(profile.getAddress());
        profile.setPhone(profile.getPhone());
        return new ResponseDto<UserProfile>(
            "CREATED", 
            "user profile created successfully", 
            userProfileRepository.save(profile)
        );
    }

    public ResponseDto<UserProfile> updateProfile(UserProfile request) {
        Integer userId = jwtService.extractUserId(servletRequest.getHeader("Authorization"));
        UserProfile profile = userProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        profile.setAddress(request.getAddress());
        profile.setPhone(request.getPhone());
        return new ResponseDto<UserProfile>(
            "UPDATED", 
            "user profile updated successfully", 
            userProfileRepository.save(profile)
        );
    }

    public ResponseDto<?> tes(User req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());
        return new ResponseDto<User>(
            "OK", 
            "okeh",
            user
        );
    }

}
