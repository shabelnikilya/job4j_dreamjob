package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("candidate/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("user", req.getSession().getAttribute("user"));
        int cityId = Integer.parseInt(req.getParameter("city"));
        DbStore.instOf().save(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        new City(cityId, DbStore.instOf().findCityById(cityId).getName()),
                        req.getParameter("nameVacancy"),
                        req.getParameter("name"),
                        req.getParameter("secondName"),
                        LocalDateTime.now()
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
