package ru.job4j.dream.servlet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.DbStoreTest;
import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateServletTest {

    private final Store store = DbStore.instOf();
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
        try (PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM candidate;" +
                        "ALTER TABLE candidate ALTER COLUMN id RESTART WITH 1;"
        )) {
            statement.execute();
        }
    }

    @Test
    public void whenCreateOneCandidate() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("city")).thenReturn("1");
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("nameVacancy")).thenReturn("vacancy");
        when(req.getParameter("name")).thenReturn("name");
        when(req.getParameter("secondName")).thenReturn("secondName");
        new CandidateServlet().doPost(req, resp);
        assertNotNull(store.findAllCandidates(false));
        assertThat(store.findCandidateById(1).getName(), is("name"));
    }

    @Test
    public void whenCreateOneCandidateAndNotAddStore() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("city")).thenReturn("1");
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("nameVacancy")).thenReturn("vacancy");
        when(req.getParameter("name")).thenReturn("name");
        when(req.getParameter("secondName")).thenReturn("secondName");
        new CandidateServlet().doPost(req, resp);
        assertNull(store.findCandidateById(1));
    }
}