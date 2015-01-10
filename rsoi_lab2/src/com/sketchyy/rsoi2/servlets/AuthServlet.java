package com.sketchyy.rsoi2.servlets;

import com.sketchyy.rsoi2.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sketchy on 17.12.2014.
 */
@WebServlet(name = "AuthServlet", urlPatterns = "/authorize")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Integer userId = Utils.authorize(username, password);

        if (userId == null) {
            request.getSession().setAttribute("error", "1");
            response.sendRedirect("index.jsp");
            return;
        }

        request.getSession().setAttribute("user", Integer.toString(userId));
        response.sendRedirect("rs/movies");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
