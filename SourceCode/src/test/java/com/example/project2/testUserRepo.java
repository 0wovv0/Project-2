//package com.example.project2;
//
//import com.example.project2.Repository.userRepositoryTest;
//import com.example.project2.Entity.UserEntity;
//import com.example.project2.model.State;
//import com.example.project2.service.userServiceTest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class testUserRepo {
//    @Autowired
//    private userServiceTest userServiceTest;
//
////    @Test
////    public void addUser(){
////        userRepositoryTest userrepo = new userRepositoryTest();
////        UserEntity user = userrepo.addUser("Hung@Gmail.com", "Hung", "123456", State.ACTIVE);
////        assertThat(user).isNotNull();
////        System.out.println(user.getId());
////        assertThat(user.getId()).isNotBlank();
////    }
////
////    @Test
////    public void addUser1(){
////        userRepositoryTest userrepo = new userRepositoryTest();
////        UserEntity user = userrepo.addUser("Hung@Gmail.com", "Hung", "123456");
////        assertThat(user).isNotNull();
////        System.out.println(user.getId());
////        assertThat(user.getId()).isNotBlank();
////        assertThat(user.getStatus()).isEqualTo(State.PENDING);
//    }
//
//    @Test
//    public void testEmail(){
//        userRepositoryTest userrepo = new userRepositoryTest();
//        userrepo.addUser("hung@gmail.com", "hung23r", "12323rf456");
//        userrepo.addUser("hung1@gmail.com", "hungfr", "123f3r2f456");
//        userrepo.addUser("hung2@gmail.com", "hun2w3fg", "12egt3456");
//        userrepo.addUser("hung3@gmail.com", "hung32rf", "1234sdg56");
//
//        assertThat(userrepo.isExistEmail("hung1@gmail.com")).isTrue();
//        assertThat(userrepo.isExistEmail("hung2@gmail.com")).isTrue();
//    }
//
//
//}
