package ru.job4j.dream.servlets;

import ru.job4j.dream.utils.ReadProperties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    private final ReadProperties properties = new ReadProperties(
            PhotoUploadServlet.class.getClassLoader().getResource("app.properties").getPath()
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("id");
        File downloadFile = new File(properties.getPath("default.image"));
        for (File file : new File(properties.getPath("default.dir")).listFiles()) {
            if (file.getName().startsWith(name)) {
                downloadFile = file;
                break;
            }
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(downloadFile)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
