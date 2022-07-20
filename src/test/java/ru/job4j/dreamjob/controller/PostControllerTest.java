package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {
    @Test
    void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        UserService userService = mock(UserService.class);
        PostController postController = new PostController(
                postService,
                cityService,
                userService
        );
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("post/posts");
    }

    @Test
    public void whenAddPost() {
        Post input = new Post(1, "New post");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        UserService userService = mock(UserService.class);
        PostController postController = new PostController(
                postService,
                cityService,
                userService
        );
        String page = postController.createPost(input, 1);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    void updatePost() {
        Post input = new Post(1, "New post");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        UserService userService = mock(UserService.class);
        PostController postController = new PostController(
                postService,
                cityService,
                userService
        );
        postController.createPost(input, 1);
        verify(postService).add(input);
        input.setName("Second post");
        String page = postController.updatePost(input, 2);
        verify(postService).update(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}