package com.example.menbosa.controller.protector.user;


import com.example.menbosa.dto.protector.user.UserDTO;
import com.example.menbosa.dto.protector.user.UserSessionDTO;
import com.example.menbosa.service.protector.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alheum/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "/protector/protectorLogin";
    }

    @PostMapping("/login")
    public String login(String proMemPhone, String proMemPassword, HttpSession session) {
        try {
            UserSessionDTO sessionDTO = userService.loginUser(proMemPhone, proMemPassword);
            session.setAttribute("proMemNum", sessionDTO.getProMemNum());
            session.setAttribute("proMemPhone", sessionDTO.getProMemName());
        } catch (IllegalStateException e) {
            return "redirect:/alheum/user/login";
        }
        return "redirect:/alheum";
    }

    @GetMapping("/join")
    public String join() {
        return "/protector/protectorJoin";
    }

    @PostMapping("/join")
    public String join(UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/alheum/user/login";
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "/protector/protectorPassword-find";
    }

}
