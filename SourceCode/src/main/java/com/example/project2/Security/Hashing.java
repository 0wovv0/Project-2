package com.example.project2.Security;


public interface Hashing {
    public String hashPasword(String password);
    public boolean validatePasword(String originalPassword, String storedPassword);

}
