package com.example.project2.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record loginRequest (
        @NotBlank(message = "username is required")
        @Email(message = "username must be an email")
        String email,

        @Size(min = 6, message = "password must be between 6 and 30", max = 30)
        String password
) {
        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }

}
