package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class IndexControl {
    private final UserService userService;

    public IndexControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = userService.userFromSession(session);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}