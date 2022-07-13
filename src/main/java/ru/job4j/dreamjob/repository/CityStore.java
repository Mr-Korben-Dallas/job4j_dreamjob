package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CityStore implements Store<City> {
    private Map<Integer, City> cities = new ConcurrentHashMap<Integer, City>();
    private final AtomicInteger cityId = new AtomicInteger();

    public CityStore() {
        cities.put(1, new City(cityId.incrementAndGet(), "Moskow"));
        cities.put(2, new City(cityId.incrementAndGet(), "Saint Petersburg"));
        cities.put(3, new City(cityId.incrementAndGet(), "Yekaterinburg"));
    }

    @Override
    public City findById(int id) {
        return cities.get(id);
    }

    @Override
    public Collection<City> findAll() {
        return cities.values();
    }

    @Override
    public boolean add(City city) {
        return cities.put(incrementId(city), city) != null;
    }

    @Override
    public boolean update(City city) {
        return cities.replace(city.getId(), city) != null;
    }

    private int incrementId(City city) {
        if (city.getId() == 0) {
            city.setId(cityId.incrementAndGet());
        }
        return city.getId();
    }
}
