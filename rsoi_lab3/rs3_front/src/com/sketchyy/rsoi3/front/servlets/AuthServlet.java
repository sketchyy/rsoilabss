package com.sketchyy.rsoi3.front.servlets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

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
@WebServlet(name = "AuthServlet", urlPatterns = "/authorize")
public class AuthServlet extends HttpServlet {
    private static Logger l = Logger.getLogger(AuthServlet.class);
    private String baseUri = "http://localhost:8080";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        l.warn("FRONT + /rs3/authorize");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3ses/authorize");

            HttpPost post = new HttpPost(uriBuilder.build());
            post.setHeader("username", username);
            post.setHeader("password", password);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                request.getSession().setAttribute("error", request.getRequestURI());
                response.sendRedirect("/rs3/auth.jsp");
            } else {
                request.getSession().setAttribute("user", username);
                response.sendRedirect("/rs3/movies?p=1&q=2");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
