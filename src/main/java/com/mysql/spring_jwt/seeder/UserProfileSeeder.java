package com.mysql.spring_jwt.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.User;
import com.mysql.spring_jwt.model.UserProfile;
import com.mysql.spring_jwt.repository.UserProfileRepository;

@Service
public class UserProfileSeeder {
    
    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile seed(User user, String address, String phone) {
        UserProfile profile = new UserProfile();
        profile.setAddress(address);
        profile.setPhone(phone);
        profile.setUser(user);
        userProfileRepository.save(profile);
        System.out.println("---SEEDER: UserProfile seed success!!");
        return profile;
    }

}
