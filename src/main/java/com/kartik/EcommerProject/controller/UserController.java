package com.kartik.EcommerProject.controller;

import com.kartik.EcommerProject.model.User;
import com.kartik.EcommerProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user){
        return userService.loginUser(user.getEmail(),user.getPassword());
    }

    @GetMapping("/getUsers")
    public List<User>getAllUsers(){
       return userService.getAllUsers();
    }

}
