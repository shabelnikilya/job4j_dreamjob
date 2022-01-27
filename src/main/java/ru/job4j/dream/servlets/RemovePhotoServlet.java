package ru.job4j.dream.servlets;

import ru.job4j.dream.utils.ReadProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RemovePhotoServlet extends HttpServlet {
    private final ReadProperties properties = new ReadProperties(
            PhotoUploadServlet.class.getClassLoader().getResource("app.properties").getPath()
    );

    public RemovePhotoServlet() {
        properties.load();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("id");
        for (File file : new File(properties.getPath("default.dir")).listFiles()) {
            if (file.getName().startsWith(name)) {
                Files.delete(file.toPath());
                break;
            }
        }
        req.getRequestDispatcher("candidates.do").forward(req, resp);
    }
}
