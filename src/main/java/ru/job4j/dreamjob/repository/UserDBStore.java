package ru.job4j.dreamjob.repository;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@ThreadSafe
@Repository
public class UserDBStore implements Store<User> {
    private final BasicDataSource pool;
    private static final String TABLE_USERS_QUERY_SELECT_ALL = "SELECT * FROM users";
    private static final String TABLE_USERS_QUERY_ADD = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
    private static final String TABLE_USERS_QUERY_UPDATE = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
    private static final String TABLE_USERS_QUERY_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public User findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_USERS_QUERY_SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            log.error("Error when findById", e);
        }
        return null;
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_USERS_QUERY_SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(
                            new User(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("email"),
                                    it.getString("password")
                            )
                    );
                }
            }
        } catch (Exception e) {
            log.error("Error when findAll", e);
        }
        return users;
    }

    @Override
    public Optional<User> add(User user) {
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
            log.error("Error when add", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_USERS_QUERY_UPDATE)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (Exception e) {
            log.error("Error when update", e);
        }
        return result;
    }
}
