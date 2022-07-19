package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserDetailService;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {
    private final UserService service;
    private final UserDetailService userDetailService;

    public UserController(UserService service, UserDetailService userDetailService) {
        this.service = service;
        this.userDetailService = userDetailService;
    }

    @PostMapping("/signup")
    public String signup(Model model, @ModelAttribute User user) {
        Optional<User> regUser = service.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/signup?fail=true";
        }
        return "redirect:/posts";
    }

    @GetMapping("/signup")
    public String signup(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("user", new User());
        model.addAttribute("fail", fail != null);
        return "user/signup";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user) {
        Optional<User> userDb = userDetailService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        System.out.println(userDb);
        if (userDb.isEmpty()) {
            return "redirect:/login?fail=true";
        }
        return "redirect:/index";
    }
}
