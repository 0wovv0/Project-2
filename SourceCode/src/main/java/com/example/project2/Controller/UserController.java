package com.example.project2.Controller;

import com.example.project2.Entity.ClassEntity;
import com.example.project2.Entity.ENUM.Accepted;
import com.example.project2.Entity.ProblemEntity;
import com.example.project2.Exception.OtherException;
import com.example.project2.Model.dto.ProblemsDTO;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Service.ProblemService;
import com.example.project2.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProblemService problemService;

    @GetMapping
    public String returnUserHomePage(HttpSession session, Model model) {
        UserDTO currentUser = session.getAttribute("currentUser") == null ? null : (UserDTO) session.getAttribute("currentUser");
//        return ResponseEntity.ok().body(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("role", currentUser.getRole());
        return "HomePage";
    }

    @PostMapping
    public ResponseEntity<?> handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return ResponseEntity.ok().body("Logout successfully!");
    }




}