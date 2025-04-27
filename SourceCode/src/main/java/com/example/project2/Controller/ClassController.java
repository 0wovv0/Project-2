package com.example.project2.Controller;


import com.example.project2.Entity.ClassEntity;
import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ProblemEntity;
import com.example.project2.Exception.OtherException;
import com.example.project2.Model.dto.ProblemsDTO;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Service.ClassService;
import com.example.project2.Service.ProblemService;
import com.example.project2.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping
public class ClassController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ClassService classService;

    @GetMapping("/classes")
    public String showClassList(HttpSession session, Model model) {
        if (session.getAttribute("currentUser") != null) {
            UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
            Set<ClassEntity> classList = userService.findClassByUserEmail(userDTO.getEmail());
            // nếu null thì xử lý bên FE
            model.addAttribute("classID", classList);
            model.addAttribute("classList", classList);
        }
        return "User/ContestDetail";
    }

    @GetMapping("/classID")
    public String showClassDetail(@RequestParam("id") String id, Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        if (userDTO != null) {
            Optional<ClassEntity> classEntity = classService.getClass(id);
            if(classEntity.isPresent()){
                Set<ProblemsDTO> problemsDTOS = new HashSet<>();
                classEntity.get().getProblemList().forEach(problemEntity -> {
                    problemsDTOS.add(new ProblemsDTO(problemEntity, 100, Accepted.ACCEPTED));
                });
                model.addAttribute("classID", id);
                model.addAttribute("problems", problemsDTOS);
                return "User/ClassDetails"; // Trả về tên của template Thymeleaf
            }

            else{
                throw new OtherException("Class not found");
            }
        } return "redirect:/login";
    }

    @GetMapping("/classDetail")
    public String showProblemDetail(
                        @RequestParam("classID") String classID,
                        @RequestParam("problemID") String problemID, Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        if (userDTO != null) {
            Optional<ClassEntity> classEntity = classService.getClass(classID);
            if(classEntity.isPresent()) {
                for (ProblemEntity problemEntity : classEntity.get().getProblemList()) {
                    if (problemEntity.getId().equals(problemID)) {
                        ProblemEntity problem = problemService.getProblemByID(problemID).get();
                        model.addAttribute("classID", classID);
                        model.addAttribute("problemID", problemID);
                        model.addAttribute("problem", new ProblemsDTO(problem, 100, Accepted.ACCEPTED));
                        return "/User/ProblemsDetail"; // Sửa đường dẫn template Thymeleaf
                    }
                }
            }


        }
        return "redirect:/login";
    }
}
