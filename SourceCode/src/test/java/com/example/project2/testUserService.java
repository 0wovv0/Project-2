//package com.example.project2;
//
//
//import com.example.project2.Entity.UserEntity;
//import com.example.project2.Entity.ENUM.State;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.*;
//
//
//@SpringBootTest
//public class testUserService {
//    @Autowired
//    private userServiceTest userService;
//
//    @Test
//    public void addUser(){
//        UserEntity tt = new UserEntity("Hung", "Hung@gmail.com", "123456piu", State.ACTIVE);
//        UserEntity user = userService.addUser();
//        assertThat(user).isNotNull();
//    }
//
//}