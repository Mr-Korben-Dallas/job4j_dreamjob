package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class PostDBStore implements Store<Post> {
    private final BasicDataSource pool;
    private final CityService cityService;
    private static final String TABLE_POSTS_QUERY_SELECT_ALL = "SELECT * FROM posts";
    private static final String TABLE_POSTS_QUERY_SELECT_BY_ID = "SELECT * FROM posts WHERE id = ?";
    private static final String TABLE_POSTS_QUERY_ADD = "INSERT INTO posts(name, city_id, description, created, is_visible) VALUES (?, ?, ?, ?, ?)";
    private static final String TABLE_POSTS_QUERY_UPDATE = "UPDATE posts SET name = ?, city_id = ?, description = ?, created = ?, is_visible = ? WHERE id = ?";

    public PostDBStore(BasicDataSource pool, CityService cityService) {
        this.pool = pool;
        this.cityService = cityService;
    }

    @Override
    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_POSTS_QUERY_SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getBoolean("is_visible"),
                            cityService.findById(it.getInt("city_id")),
                            it.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_POSTS_QUERY_SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(
                            new Post(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("description"),
                                    it.getBoolean("is_visible"),
                                    cityService.findById(it.getInt("city_id")),
                                    it.getTimestamp("created").toLocalDateTime()
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_POSTS_QUERY_ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(5, post.getIsVisible());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public boolean update(Post post) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(TABLE_POSTS_QUERY_UPDATE)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(5, post.getIsVisible());
            ps.setInt(6, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
