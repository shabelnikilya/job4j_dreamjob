package ru.job4j.dream.store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DbStoreTest {
    private final Store store = DbStore.instOf();
    private final LocalDateTime inc = LocalDateTime.now();
    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = DbStoreTest
                .class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statementPost = connection.prepareStatement(
                "DELETE FROM post;" +
                        "DELETE FROM candidate;" +
                        "ALTER TABLE post ALTER COLUMN id RESTART WITH 1;" +
                        "ALTER TABLE candidate ALTER COLUMN id RESTART WITH 1;")) {
            statementPost.execute();
            statementPost.execute();
            statementPost.execute();
            statementPost.execute();
        }
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(0, "Java Job", "Без опыта", inc);
        store.save(post);
        Post postInDb = store.findPostById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate =  new Candidate(0, "java", "roma", "kolesov");
        store.save(candidate);
        Candidate postInDb = store.findCandidateById(candidate.getId());
        assertThat(postInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenChangePost() {
        Post first = new Post(0, "Java Job", "Без опыта", inc);
        store.save(first);
        Post second = new Post(1, "Js", "С опытом работы", inc);
        store.save(second);
        Post postInDb = store.findPostById(1);
        assertThat(postInDb.getName(), is(second.getName()));
    }

    @Test
    public void whenChangeCandidate() {
        Candidate first =  new Candidate(0, "java", "roma", "kolesov");
        store.save(first);
        Candidate second =  new Candidate(1, "Js", "Grigoriy", "Shishkin");
        store.save(second);
        Candidate postInDb = store.findCandidateById(1);
        assertThat(postInDb.getName(), is(second.getName()));
    }

    @Test
    public void whenCreatePostAndHeNotFound() {
        Post post = new Post(0, "Java Job", "Без опыта", inc);
        store.save(post);
        assertNull(store.findPostById(0));
    }

    @Test
    public void whenCreateCandidateAndHeNotFound() {
        Candidate candidate =  new Candidate(0, "java", "roma", "kolesov");
        store.save(candidate);
        assertNull(store.findCandidateById(0));
    }

    @Test
    public void whenCreatePostsAndCheckAllPosts() {
        Post first = new Post(0, "Java Job", "Без опыта", inc);
        Post second = new Post(0, "Js Job", "С опытом от 1 года", inc);
        List<Post> exp = List.of(first, second);
        store.save(first);
        store.save(second);
        assertThat(store.findAllPosts(), is(exp));
    }

    @Test
    public void whenCreateCandidatesAndCheckAllCandidate() {
        Candidate first =  new Candidate(0, "java", "roma", "kolesov");
        Candidate second =  new Candidate(0, "JS", "Egor", "Yelshinov");
        List<Candidate> exp = List.of(first, second);
        store.save(first);
        store.save(second);
        assertThat(store.findAllCandidates(), is(exp));
    }

    @Test
    public void whenCreateCandidatesAndRemoveCandidate() {
        Candidate first =  new Candidate(0, "java", "roma", "kolesov");
        Candidate second =  new Candidate(0, "JS", "Egor", "Yelshinov");
        List<Candidate> exp = List.of(second);
        store.save(first);
        store.save(second);
        store.deleteCandidate(first.getId());
        assertThat(store.findAllCandidates().size(), is(1));
        assertThat(store.findAllCandidates(), is(exp));
    }
}