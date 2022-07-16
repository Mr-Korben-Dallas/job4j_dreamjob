package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateDbStore;
import ru.job4j.dreamjob.repository.Store;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {
    private final Store<Candidate> store;

    private CandidateService(CandidateDbStore store) {
        this.store = store;
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public Candidate add(Candidate candidate) {
        return store.add(candidate);
    }

    public boolean update(Candidate candidate) {
        return store.update(candidate);
    }
}
