package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Tom", "Junior Java Job"));
        posts.put(2, new Post(2, "John", "Middle Java Job"));
        posts.put(3, new Post(3, "Robert", "Senior Java Job"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public boolean add(Post post) {
        return posts.put(post.getId(), post) != null;
    }

    public boolean update(Post post) {
        return posts.replace(post.getId(), post) != null;
    }
}