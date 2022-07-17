package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.repository.PostDBStore;
import ru.job4j.dreamjob.repository.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class PostService {
    private final Store<Post> store;
    private final CityService cityService;

    private PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return store.findAll();
    }

    public Optional<Post> add(Post post) {
        return store.add(post);
    }

    public boolean update(Post post) {
        return store.update(post);
    }
}
