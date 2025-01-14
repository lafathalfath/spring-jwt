package com.mysql.spring_jwt.service.table;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mysql.spring_jwt.dto.ResponseDto;
import com.mysql.spring_jwt.model.Article;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.ArticleRepository;
import com.mysql.spring_jwt.repository.UserRepository;
import com.mysql.spring_jwt.service.auth.JwtService;
import com.mysql.spring_jwt.service.storage.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ArticleService {
    
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final FileStorageService fileStorageService;

    private final HttpServletRequest servletRequest;

    public ArticleService(
        ArticleRepository articleRepository,
        UserRepository userRepository,
        JwtService jwtService,
        FileStorageService fileStorageService,
        HttpServletRequest servletRequest
    ) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.fileStorageService = fileStorageService;
        this.servletRequest = servletRequest;
    }

    public ResponseDto<List<Article>> getAllArticles() {
        return new ResponseDto<List<Article>>(
            "OK", 
            "get articles successfully", 
            articleRepository.findAll()
        );
    }

    public ResponseDto<Article> getArticleById(Integer id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "article not found"));
        return new ResponseDto<Article>(
            "OK", 
            "get article successfully", 
            article
        );
    }

    public ResponseDto<Article> store(String title, String content, MultipartFile image) throws IOException {
        Integer userId = jwtService.extractUserId(servletRequest.getHeader("Authorization"));
        User author = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden"));
        Article article = new Article();
        article.setAuthor(author);
        article.setTitle(title);
        article.setContent(content);
        if (image != null) {
            String imageUrl = fileStorageService.store(image, "article");
            article.setImageUrl(imageUrl);
        }
        return new ResponseDto<Article>(
            "CREATED", 
            "article created successfully", 
            articleRepository.save(article)
        );
    }

    public ResponseDto<Article> update(Integer id, String title, String content, MultipartFile image) throws IOException {
        Integer userId = jwtService.extractUserId(servletRequest.getHeader("Authorization"));
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "article not found"));
        if (userId != article.getAuthor().getId()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        article.setTitle(title);
        article.setContent(content);
        if (image != null) {
            String imageUrl = fileStorageService.store(image, "article");
            article.setImageUrl(imageUrl);
        }
        Article updatedArticle = articleRepository.save(article);
        return new ResponseDto<Article>(
            "UPDATED", 
            "article updated successfully", 
            updatedArticle
        );
    }

    public ResponseDto<Void> destroy(Integer id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "article not found"));
        articleRepository.delete(article);
        return new ResponseDto<Void>(
            "DELETED", 
            "article deleted", 
            null
        );
    }

}
