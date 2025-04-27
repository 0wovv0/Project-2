package com.example.project2.Model;

import com.example.project2.Entity.ClassEntity;
import com.example.project2.Entity.ENUM.Language;
import com.example.project2.Entity.TestcaseEntity;
import com.example.project2.Entity.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Getter
@Setter
@Data
public class FileDetail {
    MultipartFile file;
    String submittedId;
    String problemId;
    ClassEntity classEntity;
    UserEntity userEntity;
    Set<TestcaseEntity> testcaseIDs;
    Language language;

    public FileDetail(MultipartFile file, String submittedId, String problemId, Set<TestcaseEntity> testcaseIDs, ClassEntity classEntity, Language language, UserEntity userEntity) {
        this.file = file;
        this.submittedId = submittedId;
        this.problemId = problemId;
        this.testcaseIDs = testcaseIDs;
        this.classEntity = classEntity;
        this.language = language;
        this.userEntity = userEntity;
    }
}
