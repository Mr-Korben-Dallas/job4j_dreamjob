package ru.job4j.dreamjob.repository;

import java.util.Collection;

public interface Store<T> {
    T findById(int id);

    Collection<T> findAll();

    boolean add(T t);

    boolean update(T t);
}
