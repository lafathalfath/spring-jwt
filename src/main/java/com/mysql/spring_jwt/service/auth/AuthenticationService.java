package com.mysql.spring_jwt.service.auth;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mysql.spring_jwt.dto.ResponseDto;
import com.mysql.spring_jwt.model.Role;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.UserRepository;
import com.mysql.spring_jwt.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest servletRequest;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            HttpServletRequest servletRequest
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.servletRequest = servletRequest;
    }

    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        // UserProfile profile = request.getUserProfile();
        // profile.setAddress(null);
        // profile.setPhone(null);
        // user.setUserProfile(profile);
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        User user = userRepository
                .findByUsernameOrEmail(request.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public HashMap<String, String> refreshAuthentication() {
        String header = servletRequest.getHeader("Authorization");
        if (header == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized");
        String token = header;
        if (header.startsWith("Bearer ")) token = header.substring(7);
        User user = userRepository.findById(jwtService.extractUserId(token))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("token", jwtService.refreshToken(token, user));
        return response;
    }

    public ResponseDto<?> getUserData() {
        String header = servletRequest.getHeader("Authorization");
        if (header == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized");
        String token = header;
        if (header.startsWith("Bearer ")) token = header.substring(7);
        User user = userRepository.findById(jwtService.extractUserId(token))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        return new ResponseDto<User>(
            "OK", 
            "get user data successfully", 
            user
        );
    }

}
