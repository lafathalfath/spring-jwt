package com.mysql.spring_jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysql.spring_jwt.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    
    Optional<UserProfile> findByUserId(Integer userId);

}
