package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.model.User;
import com.kartik.EcommerProject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;


    public User registerUser(User user) {
           return  userRepository.save(user);
    }

    public  User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user!=null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }


    public List<User> getAllUsers() {
       return   userRepository.findAll();
    }
}
