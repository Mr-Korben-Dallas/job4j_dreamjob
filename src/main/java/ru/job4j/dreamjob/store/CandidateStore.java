package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore STORE = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger CANDIDATE_ID = new AtomicInteger();

    public CandidateStore() {
        candidates.put(1, new Candidate(CANDIDATE_ID.incrementAndGet(), "JAVA Software Developer", "Scream fiery like a black tobacco."));
        candidates.put(2, new Candidate(CANDIDATE_ID.incrementAndGet(), "Jr. JAVA v.11 Developer Associate", "The sun acquires."));
        candidates.put(3, new Candidate(CANDIDATE_ID.incrementAndGet(), "JAVA Developer", "The transformator is more pathway now than species. calm and unearthly harmless."));
    }

    public static CandidateStore instOf() {
        return STORE;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public boolean add(Candidate candidate) {
        return candidates.put(incrementId(candidate), candidate) != null;
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public boolean update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate) != null;
    }

    private int incrementId(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        return candidate.getId();
    }
}
