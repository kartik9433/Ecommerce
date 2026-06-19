package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.model.User;
import com.kartik.EcommerProject.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static  org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService  userService;

     @Test
     void loginUserSucess(){
         User user = new User();
         user.setName("iii");
         user.setEmail("yyy@gmail.com");
         user.setPassword("jahfhk");

         when(userRepository.findByEmail("yyy@gmail.com")).thenReturn(user);

         User result = userService.loginUser("xyz@gmail.com","jahfhk");

         assertNotNull(result);

         assertEquals("kartik",result.getName());

         verify(userRepository).findByEmail("xyz@gmail.com");
     }

}
