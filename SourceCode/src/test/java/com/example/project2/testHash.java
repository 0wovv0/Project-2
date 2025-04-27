package com.example.project2;


import com.example.project2.Security.Hashing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class testHash {
    @Autowired
    private Hashing hashing;

    @Test
    public void testHash() {
        String password = "123456";
        String hashed = hashing.hashPasword(password);
        assertThat(hashed).isNotNull();
    }

    @Test
    public void validatePassword() {
        String password = "123456aabf";
        String hashed = hashing.hashPasword(password);
        assertThat(hashing.validatePasword(password, hashed)).isTrue();

    }
}
