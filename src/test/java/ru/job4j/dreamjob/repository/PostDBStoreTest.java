package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {
    @Test
    public void whenAddPost() {
        CityService cityService = new CityService(new CityStore());
        PostDBStore store = new PostDBStore(new Main().loadPool(), cityService);
        Post post = new Post(
                0,
                "Java Job",
                "Junior Java Job",
                true,
                cityService.findById(1)
        );
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        CityService cityService = new CityService(new CityStore());
        PostDBStore store = new PostDBStore(new Main().loadPool(), cityService);
        Post post = new Post(
                0,
                "Java Job",
                "Junior Java Job",
                true,
                cityService.findById(1)
        );
        store.add(post);
        Post existedPost = store.findById(post.getId());
        existedPost.setName("C#");
        store.update(existedPost);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo("C#");
    }

    @Test
    public void whenFindAllPosts() {
        CityService cityService = new CityService(new CityStore());
        PostDBStore store = new PostDBStore(new Main().loadPool(), cityService);
        Post firstPost = new Post(0, "Tom", "Junior Java Job", true, cityService.findById(1));
        Post secondPost = new Post(0, "John", "Middle Java Job", true, cityService.findById(2));
        Post thirdPost = new Post(0, "Robert", "Senior Java Job", true, cityService.findById(3));
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        Collection<Post> posts = new ArrayList<>(Arrays.asList(firstPost, secondPost, thirdPost));
        Collection<Post> savedPosts = store.findAll();
        assertThat(savedPosts).hasSameElementsAs(posts);
    }

    @Test
    public void whenFindByIdPost() {
        CityService cityService = new CityService(new CityStore());
        PostDBStore store = new PostDBStore(new Main().loadPool(), cityService);
        Post firstPost = new Post(0, "Tom", "Junior Java Job", true, cityService.findById(1));
        Post secondPost = new Post(0, "John", "Middle Java Job", true, cityService.findById(2));
        Post thirdPost = new Post(0, "Robert", "Senior Java Job", true, cityService.findById(3));
        store.add(firstPost);
        store.add(secondPost);
        store.add(thirdPost);
        Post post = store.findById(secondPost.getId());
        assertThat(post).isEqualTo(secondPost);
    }
}