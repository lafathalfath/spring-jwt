package com.mysql.spring_jwt.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mysql.spring_jwt.model.Role;
import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.repository.UserRepository;

@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            User admin = new User();
            admin.setFirstname("Application");
            admin.setLastname("Manager");
            admin.setUsername("Admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(Role.ADMIN);

            repository.save(admin);

            System.out.println("User seed success!!");
        }
    }

}
