package com.example.project2.Model.mapper;

import com.example.project2.Entity.UserEntity;
import com.example.project2.Model.dto.UserDTO;

public class userMapper {
    public static UserDTO toUserDto(UserEntity UserEntity) {
        UserDTO dto = new UserDTO();
        dto.setName(UserEntity.getName());
        dto.setEmail(UserEntity.getEmail());
        dto.setRole(UserEntity.getRole());
        return dto;
    }

    public static UserEntity toUserEntity(UserDTO UserDTO) {
        UserEntity entity = new UserEntity();
        entity.setName(UserDTO.getName());
        entity.setEmail(UserDTO.getEmail());
        entity.setRole(UserDTO.getRole());
        return entity;
    }
}
