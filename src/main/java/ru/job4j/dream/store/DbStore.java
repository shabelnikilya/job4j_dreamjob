package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class);
    private final BasicDataSource pool = new BasicDataSource();

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        DbStore.class.getClassLoader()
                        .getResourceAsStream("db.properties")
                ))) {
            cfg.load(io);
        } catch (IOException e) {
            LOG.error(" I/O exception", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            LOG.error("Driver-related exception (JDBC)", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new DbStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name_vacancy"),
                            it.getString("name"),
                            it.getString("second_name")));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public Post findPostById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return null;
    }

    @Override
    public Candidate findCandidateById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(it.getInt("id"),
                            it.getString("name_vacancy"),
                            it.getString("name"),
                            it.getString("second_name"));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return null;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name, description, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return post;
    }

    private void update(Post post) {
        try (Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE post SET name = ?, description = ?, created = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setInt(4, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
    }



    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name_vacancy, name, second_name) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getNameVacancy());
            ps.setString(2, candidate.getName());
            ps.setString(3, candidate.getSecondName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE candidate SET name_vacancy = ?, name = ?, second_name = ? WHERE id = ?")) {
            ps.setString(1, candidate.getNameVacancy());
            ps.setString(2, candidate.getName());
            ps.setString(3, candidate.getSecondName());
            ps.setInt(4, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
    }

    public void deleteCandidate(int id) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
    }
}
