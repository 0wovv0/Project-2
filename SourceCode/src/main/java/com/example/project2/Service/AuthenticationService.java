//package com.example.project2.Service;
//
//
//import com.example.project2.Entity.UserEntity;
//import com.example.project2.Repository.UserRepository;
//import com.example.project2.Request.loginRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
//public class AuthenticationService {
//    @Autowired
//    UserRepository userRepository;
//
//    boolean authenticated(@NotNull loginRequest loginRequest) {
//        Optional<UserEntity> user = userRepository.findById(loginRequest.getEmail());
//        if(!user.isPresent()){
//            return false;
//        }
//        else {
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
//            return passwordEncoder.matches(loginRequest.getPassword(), user.get().getHashed_password());
//        }
//    }
//}
