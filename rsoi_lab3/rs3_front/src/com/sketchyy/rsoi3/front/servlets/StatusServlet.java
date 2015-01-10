package com.sketchyy.rsoi3.front.servlets;

import com.sketchyy.rsoi3.movies.entities.Movie;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sketchy on 10.01.2015.
 */
@WebServlet(name = "StatusServlet", urlPatterns = "/status")
public class StatusServlet extends HttpServlet {
    private static Logger l = Logger.getLogger(StatusServlet.class);

    private String logicUri = "http://localhost:8080";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        l.warn("FRONT + /rs3/status");

        try {
            // Get movies cnt
            URIBuilder uriBuilder = new URIBuilder(logicUri);
            uriBuilder.setPath("/rs3mov/movies/count");
            // Send Get to backend
            HttpGet get = new HttpGet(uriBuilder.build());
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(get);
            // Copy content in UTF8
            StringWriter content = new StringWriter();
            IOUtils.copy(httpResponse.getEntity().getContent(), content, "UTF-8");
            // parse string
            request.setAttribute("movies_count", Integer.parseInt(content.toString()));

            // Get actors cnt
            uriBuilder = new URIBuilder(logicUri);
            uriBuilder.setPath("/rs3act/actors/count");
            // Send Get to backend
            get = new HttpGet(uriBuilder.build());
            client = HttpClientBuilder.create().build();
            httpResponse = client.execute(get);
            // Copy content in UTF8
            content = new StringWriter();
            IOUtils.copy(httpResponse.getEntity().getContent(), content, "UTF-8");
            // parse string
            request.setAttribute("actors_count", Integer.parseInt(content.toString()));

            request.getRequestDispatcher("/status.jsp").forward(request, response);
        } catch (URISyntaxException | IOException e) {
            request.getSession().setAttribute("error", "test");
            response.sendRedirect("/rs3/auth.jsp");
        }
    }
}
