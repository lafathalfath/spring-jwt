package com.mysql.spring_jwt.seeder;

import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.Role;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DbSeeder {

    private final UserSeeder userSeeder;
    private final UserProfileSeeder userProfileSeeder;
    private final ArticleSeeder articleSeeder;
    
    private final UserRepository userRepository;

    public DbSeeder(
        UserSeeder userSeeder,
        UserProfileSeeder userProfileSeeder,
        ArticleSeeder articleSeeder,
        UserRepository userRepository
    ) {
        this.userSeeder = userSeeder;
        this.userProfileSeeder = userProfileSeeder;
        this.articleSeeder = articleSeeder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void run() {
        if (userRepository.count() == 0) {
            User user1 = userSeeder.seed("Admin", "admin@gmail.com", "password", Role.ADMIN);
            userProfileSeeder.seed(user1, "Bogor", "081234567890");
            articleSeeder.seed(user1, "First Seeds article", "Is this succeed??");
        }
    }

}
