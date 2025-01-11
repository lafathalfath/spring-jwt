package com.mysql.spring_jwt.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.spring_jwt.model.Article;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.UserRepository;
import com.mysql.spring_jwt.service.ArticleService;
import com.mysql.spring_jwt.service.ProfileService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    
    private final ArticleService articleService;
    private final UserRepository userRepository;
    private final ProfileService profileService;

    public ArticleController(
        ArticleService articleService,
        UserRepository userRepository,
        ProfileService profileService
    ) {
        this.articleService = articleService;
        this.userRepository = userRepository;
        this.profileService = profileService;
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

    @PostMapping("/post")
    public ResponseEntity<Article> store(
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam("image") MultipartFile image,
        @RequestHeader("Authorization") String RequestHeader
    ) throws IOException {
        User author = profileService.getProfile(RequestHeader);
        Article article = articleService.post(author, title, content, image);
        return ResponseEntity.status(200).body(article);
    }

}
