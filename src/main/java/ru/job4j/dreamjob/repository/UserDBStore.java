package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

@ThreadSafe
@Repository
public class UserDBStore implements Store<User> {
    private final BasicDataSource pool;
    private static final String TABLE_USERS_QUERY_ADD = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     TABLE_USERS_QUERY_ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            user = null;
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        return false;
    }
}
