package com.mysql.spring_jwt.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.mysql.spring_jwt.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    
    private final ArticleService articleService;

    public ArticleController(
        ArticleService articleService
    ) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        return ResponseEntity
            .status(200)
            .body(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Integer id) {
        return ResponseEntity
            .status(200)
            .body(articleService.getArticleById(id));
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        return ResponseEntity
            .status(200)
            .body(articleService.store(title, content, image));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        // articleService.update(id, title, content, image)
        return ResponseEntity
            .status(200)
            .body(articleService.update(id, title, content, image));
    }

    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<?> destroy(@PathVariable Integer id) {
        return ResponseEntity
            .status(204)
            .body(articleService.destroy(id));
    }

}
