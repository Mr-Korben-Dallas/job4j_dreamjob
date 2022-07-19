package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.UserDetailStore;

import java.util.Optional;

@ThreadSafe
@Service
public class UserDetailService {
    private final UserDetailStore store;

    public UserDetailService(UserDetailStore store) {
        this.store = store;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}
