package com.sketchyy.rsoi3.front.servlets;

import com.sketchyy.rsoi3.actors.entities.Actor;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sketchy on 09.01.2015.
 */
@WebServlet(name = "MoviesServlet", urlPatterns = "/movies")
public class MoviesServlet extends javax.servlet.http.HttpServlet {
    private static Logger l = Logger.getLogger(MoviesServlet.class);

    private static String logicUri = "http://localhost:8080";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        l.warn("FRONT + /rs3/movies");

        try {
            if (request.getParameter("id") == null) {
                getMovies(request, response);
            } else {
                int id = Integer.parseInt(request.getParameter("id"));
                getMovieById(id, request, response);
            }
        } catch (URISyntaxException | IOException | ParseException e) {
            request.getSession().setAttribute("error", e.getLocalizedMessage());
            response.sendRedirect("/rs3/auth.jsp");
        }
    }

    private void getMovieById(int id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(logicUri);
        uriBuilder.setPath("/rs3mov/movies/" + id);
        // Send Get to backend
        HttpGet get = new HttpGet(uriBuilder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse httpResponse = client.execute(get);
        // Copy content in UTF8
        StringWriter content = new StringWriter();
        IOUtils.copy(httpResponse.getEntity().getContent(), content, "UTF-8");
        // Make JSON from string
        JSONParser parser = new JSONParser();
        JSONObject o = (JSONObject) parser.parse(content.toString());
        Movie m = new Movie();
        m.setMovieId(((Long) o.get("movieId")).intValue());
        m.setTitle((String) o.get("title"));
        m.setGenre((String) o.get("genre"));
        m.setYear(((Long) o.get("year")).intValue());
        m.setMoney(((Long) o.get("money")).intValue());
        m.setTaglines((String) o.get("taglines"));
        m.setCountry((String) o.get("country"));

        request.setAttribute("movie", m);

        // Get actors by movie
        uriBuilder = new URIBuilder(logicUri);
        uriBuilder.setPath("/rs3act/actors/bymovie");
        uriBuilder.setParameter("id", Integer.toString(m.getMovieId()));
        // Send Get to backend
        get = new HttpGet(uriBuilder.build());
        client = HttpClientBuilder.create().build();
        httpResponse = client.execute(get);
        // Copy content in UTF8
        content = new StringWriter();
        IOUtils.copy(httpResponse.getEntity().getContent(), content, "UTF-8");
        // Make JSON from string
        parser = new JSONParser();
        JSONArray jsonActors = (JSONArray) parser.parse(content.toString());
        // Parse list of movies from json array
        List<Actor> actors = new ArrayList<>(jsonActors.size());
        for (int i = 0; i < jsonActors.size(); i++) {
            JSONObject o1 = (JSONObject) jsonActors.get(i);
            Actor a = new Actor();
            a.setActorId(((Long) o1.get("actorId")).intValue());
            a.setName((String) o1.get("name"));
            a.setGender((String) o1.get("gender"));
            a.setCountry((String) o1.get("country"));
            a.setHeight(((Long) o1.get("height")).intValue());
            actors.add(a);
        }
        request.setAttribute("actors", actors);
        request.getRequestDispatcher("/movie.jsp").forward(request, response);
    }

    private void getMovies(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException, ParseException, ServletException {
        URIBuilder uriBuilder = new URIBuilder(logicUri);
        uriBuilder.setPath("/rs3mov/movies");
        uriBuilder.setParameter("items", request.getParameter("q"));
        uriBuilder.setParameter("page", request.getParameter("p"));

        // Send Get to backend
        HttpGet get = new HttpGet(uriBuilder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse httpResponse = client.execute(get);
        // Copy content in UTF8
        StringWriter content = new StringWriter();
        IOUtils.copy(httpResponse.getEntity().getContent(), content, "UTF-8");
        // Make JSON from string
        JSONParser parser = new JSONParser();
        JSONArray jsonMovies = (JSONArray) parser.parse(content.toString());
        // Parse list of movies from json array
        List<Movie> movies = new ArrayList<>(jsonMovies.size());
        for (int i = 0; i < jsonMovies.size(); i++) {
            JSONObject o = (JSONObject) jsonMovies.get(i);
            Movie m = new Movie();
            m.setMovieId(((Long) o.get("movieId")).intValue());
            m.setTitle((String) o.get("title"));
            m.setGenre((String) o.get("genre"));
            m.setYear(((Long) o.get("year")).intValue());
            m.setMoney(((Long) o.get("money")).intValue());
            m.setTaglines((String) o.get("taglines"));
            m.setCountry((String) o.get("country"));
            movies.add(m);
        }
        request.setAttribute("movies", movies);

        // Pagination
        request.setAttribute("pages", Arrays.asList(1, 2, 3, 4));
        request.setAttribute("pageCount", 3);

        request.getRequestDispatcher("/movies.jsp").forward(request, response);
    }
}
