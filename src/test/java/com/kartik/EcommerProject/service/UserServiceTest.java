package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.model.User;
import com.kartik.EcommerProject.repo.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

//    @Disabled
//    @Test
     @ParameterizedTest
     @ValueSource(strings = {"dohni@gmail.com"})
    public void getAllUsers(String email){

         User user = new User();
         user.setName("dhoni");
         user.setEmail("dohni@gmail.com");
         user.setPassword("77889");

         userRepository.save(user);

         User foundUser =  userRepository.findByEmail(email);

         assertNotNull(foundUser);

         assertNotEquals(user,foundUser);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a , int b , int expected){
        assertEquals(expected,a+b);
    }

//    @BeforeAll
      @AfterAll
    static void setuptest(){
        System.out.println("setup");
    }

}
