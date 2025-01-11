package com.mysql.spring_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysql.spring_jwt.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    
}
