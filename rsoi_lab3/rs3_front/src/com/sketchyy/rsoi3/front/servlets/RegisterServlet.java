package com.sketchyy.rsoi3.front.servlets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Sketchy on 09.01.2015.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private String baseUri = "http://localhost:8080";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3ses/register");

            HttpPost post = new HttpPost(uriBuilder.build());
            post.setHeader("name", request.getParameter("name"));
            post.setHeader("email", request.getParameter("email"));
            post.setHeader("username", request.getParameter("username"));
            post.setHeader("password", request.getParameter("password"));

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                request.getSession().setAttribute("error", "User already registered!");
                response.sendRedirect("/rs3/register.jsp");
                return;
            }

            request.getSession().setAttribute("user", request.getParameter("username"));
            response.sendRedirect("/rs3/movies?p=1&q=2");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
