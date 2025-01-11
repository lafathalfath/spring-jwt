package com.mysql.spring_jwt.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.spring_jwt.model.Article;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.ArticleRepository;

@Service
public class ArticleService {
    
    private final ArticleRepository articleRepository;
    private final FileStorageService fileStorageService;

    public ArticleService(ArticleRepository articleRepository, FileStorageService fileStorageService) {
        this.articleRepository = articleRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Integer id) {
        return articleRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));
    }

    public Article post(User author, String title, String content, MultipartFile image) throws IOException {
        Article article = new Article();
        article.setAuthor(author);
        article.setTitle(title);
        article.setContent(content);
        if (image != null) {
            String imageUrl = fileStorageService.store(image, "article");
            article.setImageUrl(imageUrl);
        }
        return articleRepository.save(article);
    }

}
