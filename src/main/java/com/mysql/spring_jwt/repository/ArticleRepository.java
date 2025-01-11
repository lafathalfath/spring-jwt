package com.mysql.spring_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysql.spring_jwt.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    
}
