package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.Store;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class UserService {
    private final Store<User> store;

    public UserService(Store<User> store) {
        this.store = store;
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public Collection<User> findAll() {
        return store.findAll();
    }

    public Optional<User> add(User user) {
        return Optional.ofNullable(store.add(user));
    }

    public boolean update(User user) {
        return store.update(user);
    }
}
