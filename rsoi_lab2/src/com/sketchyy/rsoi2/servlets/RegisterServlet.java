package com.sketchyy.rsoi2.servlets;

import com.sketchyy.rsoi2.entities.User;
import com.sketchyy.rsoi2.utils.Utils;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sketchy on 17.12.2014.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private EntityManager em = Utils.getEm();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User u = new User(name, email, username, password);

        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();

        response.sendRedirect("/rsoi2/rs/movies");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
