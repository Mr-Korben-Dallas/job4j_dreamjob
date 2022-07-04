package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateStore;
import ru.job4j.dreamjob.repository.Store;

import java.util.Collection;

@Service
public class CandidateService implements ru.job4j.dreamjob.service.Service<Candidate> {
    private final Store<Candidate> STORE;

    private CandidateService(CandidateStore store) {
        this.STORE = store;
    }

    @Override
    public Candidate findById(int id) {
        return STORE.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return STORE.findAll();
    }

    @Override
    public boolean add(Candidate candidate) {
        return STORE.add(candidate);
    }

    @Override
    public boolean update(Candidate candidate) {
        return STORE.update(candidate);
    }
}
