package com.mysql.spring_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysql.spring_jwt.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
