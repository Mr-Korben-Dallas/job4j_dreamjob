package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.repository.CityStore;
import ru.job4j.dreamjob.repository.Store;

import java.util.Collection;

@ThreadSafe
@Service
public class CityService {
    private final Store<City> store;

    public CityService(CityStore store) {
        this.store = store;
    }

    public City findById(int id) {
        return store.findById(id);
    }

    public Collection<City> findAll() {
        return store.findAll();
    }

    public boolean add(City city) {
        return store.add(city);
    }

    public boolean update(City city) {
        return store.update(city);
    }
}
