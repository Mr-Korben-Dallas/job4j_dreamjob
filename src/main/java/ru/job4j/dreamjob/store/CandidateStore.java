package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore STORE = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "JAVA Software Developer", "Scream fiery like a black tobacco."));
        candidates.put(2, new Candidate(2, "Jr. JAVA v.11 Developer Associate", "The sun acquires."));
        candidates.put(3, new Candidate(3, "JAVA Developer", "The transformator is more pathway now than species. calm and unearthly harmless."));
    }

    public static CandidateStore instOf() {
        return STORE;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public boolean add(Candidate candidate) {
        return candidates.put(candidate.getId(), candidate) != null;
    }
}
