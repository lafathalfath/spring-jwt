package com.mysql.spring_jwt.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.Article;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.ArticleRepository;

@Service
public class ArticleSeeder {
    
    @Autowired
    private ArticleRepository articleRepository;

    public Article seed(User author, String title, String content) {
        Article article = new Article();
        article.setAuthor(author);
        article.setTitle(title);
        article.setContent(content);
        articleRepository.save(article);
        System.out.println("---SEEDER: Article seed success!!");
        return article;
    }

}
