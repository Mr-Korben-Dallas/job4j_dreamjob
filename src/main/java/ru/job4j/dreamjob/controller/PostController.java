package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

@ThreadSafe
@Controller
public class PostController {
    private final PostService service;
    private final CityService cityService;

    public PostController(PostService service, CityService cityService) {
        this.service = service;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", service.findAll());
        return "post/posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post", service.findAll());
        model.addAttribute("cities", cityService.findAll());
        return "post/addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        service.add(post);
        return "redirect:/posts";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post, @RequestParam("city.id") int id) {
        post.setCity(cityService.findById(id));
        service.update(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", service.findById(id));
        model.addAttribute("cities", cityService.findAll());
        return "post/updatePost";
    }
}