package ru.job4j.dreamjob.repository;

import java.util.Collection;
import java.util.Optional;

public interface Store<T> {
    T findById(int id);

    Collection<T> findAll();

    Optional<T> add(T t);

    boolean update(T t);
}
