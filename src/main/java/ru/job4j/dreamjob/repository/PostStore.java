package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore implements Store<Post> {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger userid = new AtomicInteger();

    private PostStore() {
        posts.put(1, new Post(userid.incrementAndGet(), "Tom", "Junior Java Job"));
        posts.put(2, new Post(userid.incrementAndGet(), "John", "Middle Java Job"));
        posts.put(3, new Post(userid.incrementAndGet(), "Robert", "Senior Java Job"));
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
            post.setId(userid.incrementAndGet());
        }
        return post.getId();
    }
}