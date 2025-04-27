package com.example.project2.Controller;

import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ProblemEntity;
import com.example.project2.Entity.SubmitEntity;
import com.example.project2.Exception.ResponseException;
import com.example.project2.Model.dto.ProblemsDTO;
import com.example.project2.Model.dto.SubmitDTO;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Service.ProblemService;
import com.example.project2.Service.SubmitService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping()
public class ProblemController {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private SubmitService submitService;

    @RequestMapping("/problem")
    private String submitSourceCode() {
        return "problem";
    }

    @PostMapping("/api/getProblem/")
    public ResponseEntity<ProblemsDTO> getProblemData(@RequestParam String id) {
        Optional<ProblemEntity> problemEntity = problemService.getProblemByID(id);
        if(!problemEntity.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        ProblemsDTO problem = new ProblemsDTO(problemEntity.get(), 100, Accepted.ACCEPTED);
        return ResponseEntity.ok(problem);
    }

    @GetMapping("/api/getSubmition")
    public ResponseEntity<Set<SubmitEntity>> getSubmitData(
                @RequestParam String classID,
                @RequestParam String problemID, HttpSession session) {

        String currentUser = ((UserDTO) session.getAttribute("currentUser")).getEmail();
        Set<SubmitEntity> submitEntitySet = submitService.getSubmitOfEachUser(currentUser, classID, problemID);

        if(submitEntitySet.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(submitEntitySet);
    }

    @GetMapping("/api/getSubmition/{id}")
    public ResponseEntity<SubmitDTO> getDetailSubmitData(@PathVariable String id) {
        Optional<SubmitDTO> submitDTO = submitService.getSubmitDTO(id);
        if (!submitDTO.isPresent()) {
            // Add logging
            System.out.println("SubmitEntity not found for ID: " + id);
            return ResponseEntity.ok(null);
        }
        // Add logging
        return ResponseEntity.ok(submitDTO.get());
    }

}
