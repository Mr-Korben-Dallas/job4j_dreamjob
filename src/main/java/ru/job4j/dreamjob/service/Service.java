package ru.job4j.dreamjob.service;

import java.util.Collection;

public interface Service<T> {
    T findById(int id);

    Collection<T> findAll();

    boolean add(T t);

    boolean update(T t);
}
