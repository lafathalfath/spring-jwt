package com.mysql.spring_jwt.controller;

import java.io.IOException;
import java.util.List;

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

import com.mysql.spring_jwt.service.table.ArticleService;

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

    @GetMapping("/get-my-articles")
    public ResponseEntity<?> getMyArticles() {
        return ResponseEntity.status(200)
            .body(articleService.getMyArticles());
    }

    @PostMapping("/store")
    public ResponseEntity<?> store(
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam(required = false) MultipartFile image,
        @RequestParam(required = false, name = "categories") List<Integer> categoriesId
    ) throws IOException {
        return ResponseEntity
            .status(200)
            .body(articleService.store(title, content, image, categoriesId));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam(required = false) MultipartFile image,
        @RequestParam(required = false, name = "categories") List<Integer> categoriesId
    ) throws IOException {
        return ResponseEntity
            .status(200)
            .body(articleService.update(id, title, content, image, categoriesId));
    }

    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<?> destroy(@PathVariable Integer id) {
        return ResponseEntity
            .status(204)
            .body(articleService.destroy(id));
    }

    

}
