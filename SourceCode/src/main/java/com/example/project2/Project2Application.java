package com.example.project2;

import com.example.project2.Entity.*;
import com.example.project2.Entity.ENUM.Level;
import com.example.project2.Entity.ENUM.Role;
import com.example.project2.Entity.ENUM.State;
import com.example.project2.Entity.ENUM.Tag;
import com.example.project2.Security.Hashing;
import com.example.project2.Service.SubmitService;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@SpringBootApplication
public class Project2Application implements CommandLineRunner {
    @Autowired
    private Hashing hashing;
    @Autowired
    private EntityManager additionalUser;
    @Autowired
    private SubmitService submitService;

    public static void main(String[] args) {
        SpringApplication.run(Project2Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        Faker faker = new Faker();
////
//        UserEntity mainUser = UserEntity.builder()
//                .Name(faker.name().fullName())
//                .Email("Hung.pv215390@sis.hust.edu.vn")
//                .role(Role.USER)
//                .hashed_password(hashing.hashPasword("123456"))
//                .state(State.ACTIVE)
//                .classList(new HashSet<>())
//                .build();
//        for(int i = 0; i < 10; ++i){
//            var user = UserEntity.builder()
//                    .Name(faker.name().fullName())
//                    .Email(faker.internet().emailAddress())
//                    .hashed_password(hashing.hashPasword(faker.internet().password()))
//                    .state(State.ACTIVE)
//                    .classList(new HashSet<>())
//                    .numberOfAccept(1)
//                    .build();
//
//            String randomDateTimeString = faker.date().past(100, java.util.concurrent.TimeUnit.DAYS).toString();
//            LocalDateTime randomDateTime = LocalDateTime.parse(randomDateTimeString, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy"));
//
//            var classRoom = ClassEntity.builder()
//                    .id(faker.idNumber().valid())
//                    .name(faker.name().name())
//                    .type(faker.company().profession())
//                    .status(State.ACTIVE)
//                    .createdAt(randomDateTime)
//                    .createdBy(faker.name().name())
//                    .studentList(new HashSet<>())
//                    .teacher(mainUser)
//                    .problemList(new HashSet<>())
//                    .build();
//
//            var testCase = TestcaseEntity.builder()
//                    .input(faker.lorem().paragraph())
//                    .output(faker.lorem().paragraph())
//                    .id(faker.idNumber().valid())
//                    .point(faker.number().numberBetween(10, 100))
//                    .validTimeRunning(faker.number().numberBetween(100, 2000))
//                    .problem(null)
//                    .build();
//
//            var problems = ProblemEntity.builder()
//                    .name(faker.name().name())
//                    .id(faker.idNumber().valid())
//                    .description(faker.lorem().paragraph())
//                    .level(faker.options().option(Level.class))
//                    .tags(faker.options().option(Tag.class))
//                    .classList(new HashSet<>())
//                    .input(faker.lorem().paragraph())
//                    .output(faker.lorem().paragraph())
//                    .testcaseList(new HashSet<>())
//                    .build();
//
//            // Add the user and teacher to the class
//            classRoom.getStudentList().add(user);
//            classRoom.getProblemList().add(problems);
//            mainUser.getClassList().add(classRoom);
//
//            // Add the class to the user and teacher
//            user.getClassList().add(classRoom);
//            mainUser.getClassList().add(classRoom);
//
//            // Add the problem to the class
//
//            problems.getClassList().add(classRoom);
//            problems.getTestcaseList().add(testCase);
//
//            // ADd problems to the testcase
//            testCase.setProblem(problems);
//
//            // Persist the entities
////            additionalUser.persist(testCase);
////            additionalUser.persist(user);
////            additionalUser.persist(classRoom);
////            additionalUser.persist(problems);
//        }
//
//        additionalUser.persist(mainUser);
//
//        additionalUser.flush();
    }

    // Add this method to the Project2Application class

}