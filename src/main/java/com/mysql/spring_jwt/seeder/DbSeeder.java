package com.mysql.spring_jwt.seeder;

import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.Role;
import com.mysql.spring_jwt.model.User;

import jakarta.annotation.PostConstruct;

@Service
public class DbSeeder {

    private final UserSeeder userSeeder;
    private final UserProfileSeeder userProfileSeeder;
    private final ArticleSeeder articleSeeder;

    public DbSeeder(
        UserSeeder userSeeder,
        UserProfileSeeder userProfileSeeder,
        ArticleSeeder articleSeeder
    ) {
        this.userSeeder = userSeeder;
        this.userProfileSeeder = userProfileSeeder;
        this.articleSeeder = articleSeeder;
    }

    @PostConstruct
    public void run() {
        User user1 = userSeeder.seed("Admin", "admin@gmail.com", "password", Role.ADMIN);
        userProfileSeeder.seed(user1, "Bogor", "081234567890");
        articleSeeder.seed(user1, "First Seeds article", "Is this succeed??");
    }

}
