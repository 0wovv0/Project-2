package com.example.project2.Controller;


import com.example.project2.Entity.ClassEntity;
import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ProblemEntity;
import com.example.project2.Entity.SubmitEntity;
import com.example.project2.Entity.UserEntity;
import com.example.project2.Model.dto.ProblemsDTO;
import com.example.project2.Model.dto.SubmitDTO;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Model.mapper.userMapper;
import com.example.project2.Service.ClassService;
import com.example.project2.Service.ProblemService;
import com.example.project2.Service.SubmitService;
import com.example.project2.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping()
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ClassService classService;
    @Autowired
    private SubmitService submitService;


    @GetMapping("/admin")
    public String returnAdminHomePage(HttpSession session, Model model) {
        UserDTO currentUser = session.getAttribute("currentUser") == null ? null : (UserDTO) session.getAttribute("currentUser");
//        return ResponseEntity.ok().body(currentUser);
        model.addAttribute("currentUser", currentUser);
        return "/Admin/AdminHomePage";
    }

    @PostMapping("/admin")
    public ResponseEntity<?> handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return ResponseEntity.ok().body("Logout successfully!");
    }

    @GetMapping("teaching")
    public String showTeachingPage(HttpSession session, Model model) {
        if (session.getAttribute("currentUser") != null) {
            UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
            UserEntity user = userService.getUser(userDTO.getEmail()).get();
            model.addAttribute("classList", user.getClassTeachingList());
            System.out.println(user.getClassTeachingList().size());
            return "Admin/TeachingDetail";
        }else {
            return "redirect:/login";
        }
    }

    @GetMapping("/yourClass")
    public String showClassList(@RequestParam String id, HttpSession session, Model model) {
        if (session.getAttribute("currentUser") != null) {
            UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
            UserEntity user = userService.getUser(userDTO.getEmail()).get();

            ClassEntity classEntity = classService.getClass(id).get();
            Set<ProblemEntity> problemEntitySet = classEntity.getProblemList();
            Set<UserEntity> userEntitySet = classEntity.getStudentList();
            Set<SubmitEntity> submitEntities = submitService.getStSubmitOfClass(id);

            model.addAttribute("classID", id);
            System.out.println("Class ID: " + id);
            model.addAttribute("submits", submitEntities);
            model.addAttribute("problems", problemEntitySet);
            model.addAttribute("students", userEntitySet);

            // nếu null thì xử lý bên FE

        }else {
            return "redirect:/login";
        }
        return "/Admin/MyClasssDetail";
    }


    @GetMapping("/addNewProblem")
    public String showAddNewProblemForm(@RequestParam String id ,Model model) {
        model.addAttribute("problem", new ProblemEntity());
        List<ProblemEntity> problems = problemService.getAllProblems();
        model.addAttribute("problems", problems);
        model.addAttribute("classID", id);

        Set<ProblemEntity> existProblems = classService.getAllProblems(id);
        System.out.println(existProblems.size() + " " + id);


        model.addAttribute("existProblems", existProblems);
        return "/Admin/AddProblem"; // Thymeleaf template name
    }



    @PostMapping("/addProblemToClass")
    public ResponseEntity<String> addProblemToClass(@RequestBody Map<String, String> payload) {
        String problemID = payload.get("problemID");
        String classID = payload.get("classID");

        Optional<ProblemEntity> problemEntity = problemService.getProblemByID(problemID);
        Optional<ClassEntity> classEntity = classService.getClass(classID);
        if(!problemEntity.isEmpty() && !classEntity.isEmpty()) {
            classEntity.get().addProblem(problemEntity.get());
            problemService.updateProblem(problemEntity.get());
            return ResponseEntity.ok().body("Thêm thành công");
        }
        //
        return ResponseEntity.badRequest().body("Thêm thất bại");
    }

    @PostMapping("/removeProblemFromClass")
    public ResponseEntity<String> removeProblemFromClass(@RequestBody Map<String, String> payload) {
        String problemID = payload.get("problemID");
        String classID = payload.get("classID");

        Optional<ProblemEntity> problemEntity = problemService.getProblemByID(problemID);
        Optional<ClassEntity> classEntity = classService.getClass(classID);
        if(!problemEntity.isEmpty() && !classEntity.isEmpty()) {
            for(ProblemEntity problem : classEntity.get().getProblemList()) {
                if(problem.getId().equals(problemID)) {
                    classEntity.get().removeProblem(problem);
                    classService.updateClass(classEntity.get());
                    return ResponseEntity.ok().body("Xóa thành công.");
                }
            }
        }
        // Xử lý xóa problemID khỏi classID
        // ...

        return ResponseEntity.badRequest().body("Xóa thất bại.");
    }

    @GetMapping("/showSubmitDetail")
    public String showSubmitDetail(@RequestParam String id, @RequestParam String problemID, Model model) {
        ProblemEntity problemEntity = problemService.getProblemByID(problemID).get();
        ProblemsDTO problemsDTO = new ProblemsDTO(problemEntity, 100, Accepted.ACCEPTED);
        model.addAttribute("problem", problemsDTO);
        SubmitDTO submitDTO = submitService.getSubmitDTO(id).get();
        model.addAttribute("submit", submitDTO);
        System.out.println(problemsDTO + "      " + submitDTO);
        return "/Admin/SubmitDetail";
    }

    @GetMapping("/addStudent")
    public String showAddStudent(@RequestParam String id ,Model model) {
        Set<UserDTO> students = userService.getAllUser();
        model.addAttribute("students", students);
        ClassEntity classEntity = classService.getClass(id).get();
        Set<UserDTO> existStudents = new HashSet<>();
        for(UserEntity student : classEntity.getStudentList()) {
            existStudents.add(userMapper.toUserDto(student));
        }

        model.addAttribute("existStudents", existStudents);

        System.out.println(students.size() + " " + id);
        System.out.println(existStudents.size() + " " + id);

        return "/Admin/AddStudent"; // Thymeleaf template name
    }

    @GetMapping("/showProblemDetail")
    public String showProblemDetail(
            @RequestParam("id") String problemID, Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        if (userDTO != null) {
            ProblemEntity problem = problemService.getProblemByID(problemID).get();
            model.addAttribute("problem", new ProblemsDTO(problem, 100, Accepted.ACCEPTED));
            Set<SubmitEntity> submitEntities = submitService.getStSubmitOfProblem(problemID);
            model.addAttribute("submits", submitEntities);
            return "/Admin/ShowProblemDetail"; // Sửa đường dẫn template Thymeleaf
        }
        return "redirect:/login";
    }


}