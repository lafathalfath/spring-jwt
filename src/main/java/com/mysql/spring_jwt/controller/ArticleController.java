package com.mysql.spring_jwt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.spring_jwt.model.Article;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.UserRepository;
import com.mysql.spring_jwt.service.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    
    private final ArticleService articleService;
    private final UserRepository userRepository;

    public ArticleController(
        ArticleService articleService,
        UserRepository userRepository
    ) {
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    @GetMapping("/user")
    public User getUser() {
        User user = userRepository.findAll().get(0);
        return user;
    }

}
