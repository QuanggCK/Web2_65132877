package clc65.quanggck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import clc65.quanggck.models.User;
import clc65.quanggck.services.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {

        if (userService.existsByUsername(user.getUsername())) {

            model.addAttribute("error",
                    "Tên đăng nhập đã tồn tại!");

            return "register";
        }

        if (userService.existsByEmail(user.getEmail())) {

            model.addAttribute("error",
                    "Email đã được sử dụng!");

            return "register";
        }

        user.setRole("USER");

        userService.save(user);

        return "redirect:/login";
    }
}