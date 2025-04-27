package com.example.project2.Model.dto;

import com.example.project2.Entity.ENUM.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String Name;
    private String Email;
    private Role role;

    @Override
    public String toString() {
        System.out.print("call ToString");
        return "UserDTO{" +
                "Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Role=" + role +
                '}';
    }
}
