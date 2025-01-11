package com.mysql.spring_jwt.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.model.UserProfile;
import com.mysql.spring_jwt.repository.UserProfileRepository;
import com.mysql.spring_jwt.repository.UserRepository;

// @Component
public class UserProfileSeeder implements CommandLineRunner {
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;

    @Override
    public void run(String ...args) throws Exception {
        if (userProfileRepository.count() == 0 && userRepository.count() == 1) {
            User user = userRepository.findAll().get(0);
            UserProfile profile = new UserProfile();
            profile.setAddress("Bogor");
            profile.setPhone("081234567890");
            profile.setUser(user);
            userProfileRepository.save(profile);
            System.out.println("---SEEDER: UserProfile seed success!!");
        }
    }

}
