package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateStore;
import ru.job4j.dreamjob.repository.Store;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService implements ru.job4j.dreamjob.service.Service<Candidate> {
    private final Store<Candidate> store;

    private CandidateService(CandidateStore store) {
        this.store = store;
    }

    @Override
    public Candidate findById(int id) {
        return store.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    @Override
    public boolean add(Candidate candidate) {
        return store.add(candidate);
    }

    @Override
    public boolean update(Candidate candidate) {
        return store.update(candidate);
    }
}
