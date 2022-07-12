package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.repository.PostStore;
import ru.job4j.dreamjob.repository.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;

@ThreadSafe
@Service
public class PostService implements ru.job4j.dreamjob.service.Service<Post> {
    private final Store<Post> store;

    private PostService(PostStore store) {
        this.store = store;
    }

    @Override
    public Post findById(int id) {
        return store.findById(id);
    }

    @Override
    public Collection<Post> findAll() {
        return store.findAll();
    }

    @Override
    public boolean add(Post post) {
        return store.add(post);
    }

    @Override
    public boolean update(Post post) {
        return store.update(post);
    }
}
