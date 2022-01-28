package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("post/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("user", req.getSession().getAttribute("user"));
        DbStore.instOf().save(
                new Post(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("description"),
                LocalDateTime.now()));
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
