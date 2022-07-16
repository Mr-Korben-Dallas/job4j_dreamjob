package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore implements Store<Candidate> {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger candidateId = new AtomicInteger();

    public CandidateStore() {
        candidates.put(1, new Candidate(candidateId.incrementAndGet(), "JAVA Software Developer", "Scream fiery like a black tobacco."));
        candidates.put(2, new Candidate(candidateId.incrementAndGet(), "Jr. JAVA v.11 Developer Associate", "The sun acquires."));
        candidates.put(3, new Candidate(candidateId.incrementAndGet(), "JAVA Developer", "The transformator is more pathway now than species. calm and unearthly harmless."));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        return candidates.put(incrementId(candidate), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public boolean update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate) != null;
    }

    private int incrementId(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(candidateId.incrementAndGet());
        }
        return candidate.getId();
    }
}
