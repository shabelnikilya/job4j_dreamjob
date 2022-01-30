package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (DbStore.instOf().findUserByEmail(email) != null) {
            req.setAttribute("error", "Пользователь с данной почтой уже зарегестрирован!");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            DbStore.instOf().save(new User(name, email, password));
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
