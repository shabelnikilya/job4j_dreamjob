package ru.job4j.dream.servlets;

import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.utils.ReadProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveCandidateServlet extends HttpServlet {
    private final ReadProperties properties = new ReadProperties(
            PhotoUploadServlet.class.getClassLoader().getResource("app.properties").getPath()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("id");
        DbStore.instOf().deleteCandidate(Integer.parseInt(name));
        req.getRequestDispatcher("/removePhoto").forward(req, resp);
    }
}
