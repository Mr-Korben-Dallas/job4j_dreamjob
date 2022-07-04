package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.repository.PostStore;
import ru.job4j.dreamjob.repository.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostService implements ru.job4j.dreamjob.service.Service<Post> {
    private final Store<Post> STORE;

    private PostService(PostStore store) {
        this.STORE = store;
    }

    @Override
    public Post findById(int id) {
        return STORE.findById(id);
    }

    @Override
    public Collection<Post> findAll() {
        return STORE.findAll();
    }

    @Override
    public boolean add(Post post) {
        return STORE.add(post);
    }

    @Override
    public boolean update(Post post) {
        return STORE.update(post);
    }
}
