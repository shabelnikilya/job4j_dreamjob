package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
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
import ru.job4j.dream.model.User;

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
    public Collection<Post> findAllPosts(boolean inLastDay) {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT * FROM post"
                     + (inLastDay ? " WHERE created::DATE BETWEEN now()::DATE - 1 AND now()::DATE;" : ";"))) {
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
    public Collection<Candidate> findAllCandidates(boolean inLastDay) {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT can.id AS id, ci.id AS city_id, ci.name AS city_name, can.name_vacancy"
                             + ", can.name, can.second_name, can.created"
                             + " FROM candidate can JOIN city ci ON can.city_id = ci.id "
                     + (inLastDay ? "WHERE can.created::DATE BETWEEN now()::DATE - 1 AND now()::DATE;" : ";")
             )
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            new City(
                                    it.getInt("city_id"),
                                    it.getString("city_name")
                            ),
                            it.getString("name_vacancy"),
                            it.getString("name"),
                            it.getString("second_name"),
                            it.getTimestamp("created").toLocalDateTime()));
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
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT can.id AS id, ci.id AS city_id, ci.name AS city_name,"
                             + " can.name_vacancy, can.name, can.second_name, can.created"
                             + " FROM candidate can JOIN city ci ON can.city_id = ci.id  WHERE can.id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(it.getInt("id"),
                            new City(
                                    it.getInt("city_id"),
                                    it.getString("city_name")
                            ),
                            it.getString("name_vacancy"),
                            it.getString("name"),
                            it.getString("second_name"),
                            it.getTimestamp("created").toLocalDateTime()
                    );
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

    @Override
    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(new User(it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return users;
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    @Override
    public User findUserById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"),
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password"));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return null;
    }

    @Override
    public List<City> allCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM city;")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return cities;
    }

    @Override
    public City findCityById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT name FROM city WHERE id = ?;")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    return new City(id, it.getString("name"));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return null;
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, description, created) VALUES (?, ?, ?)",
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
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name_vacancy, name, second_name, city_id, created) "
                             + "VALUES (?, ?, ?, ?, ?);",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getNameVacancy());
            ps.setString(2, candidate.getName());
            ps.setString(3, candidate.getSecondName());
            ps.setInt(4, candidate.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(candidate.getCreatedCandidate()));
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
                     "UPDATE candidate SET name_vacancy = ?, name = ?,"
                             + " second_name = ?, city_id = ?, created = ? WHERE id = ?")) {
            ps.setString(1, candidate.getNameVacancy());
            ps.setString(2, candidate.getName());
            ps.setString(3, candidate.getSecondName());
            ps.setInt(4, candidate.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(candidate.getCreatedCandidate()));
            ps.setInt(6, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
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
        } catch (SQLException e) {
            LOG.error("SQL exception in DbStore", e);
        }
        return user;
    }

    private void update(User user) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
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
