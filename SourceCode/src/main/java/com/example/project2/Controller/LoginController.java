package com.example.project2.Controller;

import com.example.project2.Entity.ENUM.Role;
import com.example.project2.Entity.UserEntity;
import com.example.project2.Exception.ResponseException;
import com.example.project2.Model.dto.UserDTO;
import com.example.project2.Request.loginRequest;
import com.example.project2.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String showLoginPage(Model model, HttpSession session){
        if(session.getAttribute("currentUser") != null){
            return "redirect:/user";
        }
        model.addAttribute("loginrequest", new loginRequest("", ""));
        return "login";
    }

    @PostMapping("login")
    public ResponseEntity<?> handleLogin(@Valid @ModelAttribute loginRequest loginRequest, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("User name or password is malformed!");
        } else {
            try {
                UserEntity user = userService.login(loginRequest.getEmail(), loginRequest.getPassword()).get();
                UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), user.getRole());
                session.setAttribute("currentUser", userDTO);
                if(user.getRole().equals(Role.ADMIN))
                    return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/admin").build();
                return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/user").build();

            }catch (ResponseException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session){
        session.removeAttribute("currentUser");
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String showRegisterPage(){
        return "foo";
    }

    @GetMapping("/foo")
    public String userException() {
        throw new ResponseException("Some error occurred!");
    }
}