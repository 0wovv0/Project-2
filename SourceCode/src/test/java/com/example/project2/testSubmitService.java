package com.example.project2;


import com.example.project2.Entity.SubmitEntity;
import com.example.project2.Security.Hashing;
import com.example.project2.Service.SubmitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class testSubmitService {
    @Autowired
    private SubmitService submitService;


//    public Set<SubmitEntity> getSubmitOfEachUser(String userID, String classID, String problemID) {
//    }
    @Test
    public void testHash() {
        Set<SubmitEntity> submitEntities = submitService.getSubmitOfEachUser("hungsatthu1412@gmail.com", "850-23-3683", "111111111");
        assertThat(!submitEntities.isEmpty()).isTrue();
    }

}
