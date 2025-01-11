package com.mysql.spring_jwt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.Article;
import com.mysql.spring_jwt.repository.ArticleRepository;

@Service
public class ArticleService {
    
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Integer id) {
        return articleRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));
    }

}
