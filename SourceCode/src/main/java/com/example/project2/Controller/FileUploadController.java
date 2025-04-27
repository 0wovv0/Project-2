package com.example.project2.Controller;

import com.example.project2.Entity.*;
import com.example.project2.Entity.ENUM.Language;
import com.example.project2.Model.FileDetail;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Service.*;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Transactional
@Controller
public class FileUploadController {
    @Autowired
    ProblemService problemService;
    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExeMultiPartFile exeMultiPartFile;
    @Autowired
    private SubmitService submitService;

    private LinkedBlockingQueue<FileDetail> fileQueue;
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        // Khởi tạo hàng đợi và ExecutorService
        fileQueue = new LinkedBlockingQueue<>();
        executorService = Executors.newSingleThreadExecutor();

        // Bắt đầu một luồng xử lý các tệp trong hàng đợi
        executorService.submit(this::processQueue);
    }

    @Transactional
    public void processQueue() {
        while (true) {
            try {
                // Lấy tệp từ hàng đợi
                SubmitEntity submitEntity = exeMultiPartFile.CompileAndRunCpp(fileQueue.take());
                submitService.saveSubmitEntity(submitEntity);
                System.out.println("File processed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("classID") String classID,
            @RequestParam("problemID") String problemID,
            @RequestParam("test") MultipartFile file, HttpSession session) {
        Faker faker = new Faker();
        String id = faker.idNumber().valid();
        Optional<ProblemEntity> problemEntity = problemService.getProblemByID(problemID);
        if(problemEntity.isPresent()){
            Set<TestcaseEntity> testcaseEntityList = problemEntity.get().getTestcaseList();
            Optional<ClassEntity> classEntity = classService.getClass((classID));
            UserEntity userEntity = userService.getUser(((UserDTO) session.getAttribute("currentUser")).getEmail()).get();
            classEntity.ifPresent(entity -> fileQueue.add(new FileDetail(file, id, problemID, testcaseEntityList, entity, Language.CPP, userEntity)));
            return ResponseEntity.ok().body("File uploaded successfully");
        }

        // Thêm tệp vào hàng đợi
        return ResponseEntity.badRequest().body("File uploaded successfully");
    }

}