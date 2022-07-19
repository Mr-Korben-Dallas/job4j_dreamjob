package ru.job4j.dreamjob.repository;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Slf4j
@ThreadSafe
@Repository
public class UserDetailStore {
    private final BasicDataSource pool;
    private static final String TABLE_USERS_QUERY_SELECT_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE email = ? and password = ?";

    public UserDetailStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_USERS_QUERY_SELECT_BY_EMAIL_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                System.out.println(it);
                System.out.println(it);
                System.out.println(it);
                if (it.next()) {
                    return Optional.of(new User(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    ));
                }
            }
        } catch (Exception e) {
            log.error("Error when findUserByEmailAndPwd", e);
        }
        return Optional.empty();
    }
}
