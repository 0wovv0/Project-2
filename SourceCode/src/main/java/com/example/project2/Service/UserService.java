package com.example.project2.Service;


import com.example.project2.Entity.ClassEntity;
import com.example.project2.Entity.UserEntity;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Model.mapper.userMapper;
import com.example.project2.Repository.ClassRepository;
import com.example.project2.Repository.UserRepository;
import com.example.project2.Security.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.project2.Exception.ResponseException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Hashing hashing;

    public void addUser(UserEntity userEntity) {
        try{
            if(!userRepository.existsById(userEntity.getEmail().toLowerCase())) {
                userRepository.save(userEntity);
            }
            else{
                throw new ResponseException("User is already exist");
            }
        } catch (Exception e) {
            throw new ResponseException("Some thing wrong!");
        }
    }

    public Optional<UserEntity> getUser(String email) {
        try {
            return userRepository.findById(email);
        } catch (Exception e) {
            throw new ResponseException("Some thing wrong on getUser!");
        }
    }

    public void updateUser(UserEntity userEntity) {
        try{
            if(!userRepository.existsById(userEntity.getEmail().toLowerCase())) {
                throw new ResponseException("User is not exist");
            }
            else{
                userRepository.save(userEntity);
            }
        }catch (ResponseException e) {
            throw new ResponseException("Some thing wrong on updateUser!");
        }
    }

    public Optional<UserEntity> login(String email, String password){
        Optional<UserEntity> currentUser = userRepository.findById(email);
        if(!currentUser.isPresent()){
            throw new ResponseException("User is not exist!");
        }
        if(currentUser.get().getHashed_password().equals(hashing.hashPasword(password))){
            return currentUser;
        }
        else{
            throw new ResponseException("Password is not correct");
        }
    }

    public Set<ClassEntity> findClassByUserEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findById(email);
        if (userOptional.isPresent()) {
            return userOptional.get().getClassList();
        } else {
            throw new ResponseException("User with email " + email + " does not exist");
        }
    }

    public Set<UserDTO> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        Set<UserDTO>  userDTOS= new HashSet<>();
        for (UserEntity userEntity : userEntities) {
            userDTOS.add(userMapper.toUserDto(userEntity));
        }
        return userDTOS;
    }

}