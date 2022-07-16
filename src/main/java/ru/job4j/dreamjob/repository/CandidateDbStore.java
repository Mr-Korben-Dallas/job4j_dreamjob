package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Repository
public class CandidateDbStore implements Store<Candidate> {
    private final BasicDataSource pool;
    private static final String TABLE_POSTS_QUERY_SELECT_ALL = "SELECT * FROM candidates";
    private static final String TABLE_POSTS_QUERY_SELECT_BY_ID = "SELECT * FROM candidates WHERE id = ?";
    private static final String TABLE_POSTS_QUERY_ADD = "INSERT INTO candidates(name, description, created, photo) VALUES (?, ?, ?, ?)";
    private static final String TABLE_POSTS_QUERY_UPDATE = "UPDATE candidates SET name = ?, description = ?, created = ?, photo = ? WHERE id = ?";

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(TABLE_POSTS_QUERY_SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBytes("photo")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_POSTS_QUERY_SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(
                            new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("description"),
                                    it.getTimestamp("created").toLocalDateTime(),
                                    it.getBytes("photo")
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     TABLE_POSTS_QUERY_ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    @Override
    public boolean update(Candidate candidate) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(TABLE_POSTS_QUERY_UPDATE)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
