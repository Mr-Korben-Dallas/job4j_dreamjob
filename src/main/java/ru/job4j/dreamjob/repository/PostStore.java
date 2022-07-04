package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore implements Store<Post> {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger USER_ID = new AtomicInteger();

    private PostStore() {
        posts.put(1, new Post(USER_ID.incrementAndGet(), "Tom", "Junior Java Job"));
        posts.put(2, new Post(USER_ID.incrementAndGet(), "John", "Middle Java Job"));
        posts.put(3, new Post(USER_ID.incrementAndGet(), "Robert", "Senior Java Job"));
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public boolean add(Post post) {
        return posts.put(incrementId(post), post) != null;
    }

    public boolean update(Post post) {
        return posts.replace(post.getId(), post) != null;
    }

    private int incrementId(Post post) {
        if (post.getId() == 0) {
            post.setId(USER_ID.incrementAndGet());
        }
        return post.getId();
    }
}