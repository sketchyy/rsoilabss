package com.sketchyy.rsoi3.front.servlets;

import com.sketchyy.rsoi3.movies.entities.Movie;
import com.sketchyy.rsoi3.movies.resources.MovieResource;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * Created by Sketchy on 09.01.2015.
 */
@WebServlet(name = "ActionServlet", urlPatterns = "/movieaction")
public class MovieActionServlet extends HttpServlet {
    private static Logger l = Logger.getLogger(MovieActionServlet.class);

    private String baseUri = "http://localhost:8080";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        l.warn("FRONT + /rs3/movieaction");

        String act = request.getParameter("act");
        switch (act) {
            case "delete": delete(request, response);
                break;
            case "create": create(request, response);
                break;
            case "update": update(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        l.warn("FRONT + /rs3/movieaction");

        String act = request.getParameter("act");
        switch (act) {
            case "delete": delete(request, response);
                break;
            case "create": create(request, response);
                break;
            case "update": update(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        Movie m = new Movie();
        m.setMovieId(Integer.parseInt(request.getParameter("movieId")));
        m.setTitle(request.getParameter("title"));
        m.setGenre(request.getParameter("genre"));
        m.setYear(Integer.parseInt(request.getParameter("year")));
        m.setMoney(Integer.parseInt(request.getParameter("money")));
        m.setTaglines(request.getParameter("taglines"));
        m.setCountry(request.getParameter("country"));

        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3mov/movies/");

            HttpPut put = new HttpPut(uriBuilder.build());
            put.addHeader("content-type", "application/json");

            StringEntity str = new StringEntity(m.buildJSON().toJSONString());
            put.setEntity(str);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(put);

            response.sendRedirect("/rs3/movies?p=1&q=2");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }


    private void create(HttpServletRequest request, HttpServletResponse response) {
        Movie m = new Movie();
        m.setMovieId(getMaxId());
        m.setTitle(request.getParameter("title"));
        m.setGenre(request.getParameter("genre"));
        m.setYear(Integer.parseInt(request.getParameter("year")));
        m.setMoney(Integer.parseInt(request.getParameter("money")));
        m.setTaglines(request.getParameter("taglines"));
        m.setCountry(request.getParameter("country"));

        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3mov/movies");

            HttpPost post = new HttpPost(uriBuilder.build());
            post.addHeader("content-type", "application/json");

            StringEntity str = new StringEntity(m.buildJSON().toJSONString());
            post.setEntity(str);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == Response.Status.CONFLICT.getStatusCode()) {
                request.getSession().setAttribute("error", "already exists");
                response.sendRedirect("/rs3/moviecreate.jsp");
            } else {
                response.sendRedirect("/rs3/movies?p=1&q=2");
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3mov/movies/" + request.getParameter("id"));

            HttpDelete delete = new HttpDelete(uriBuilder.build());

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(delete);

            response.sendRedirect("/rs3/movies?p=1&q=2");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private int getMaxId() {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3mov/movies/maxid");

            HttpGet get = new HttpGet(uriBuilder.build());
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(get);

            // Copy content in UTF8
            StringWriter content = new StringWriter();
            IOUtils.copy(httpResponse.getEntity().getContent(), content, "UTF-8");

            return Integer.parseInt(content.toString());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
