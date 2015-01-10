package com.sketchyy.rsoi2.resources;

import com.sketchyy.rsoi2.utils.Utils;
import com.sketchyy.rsoi2.entities.Movie;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sketchy on 14.12.2014.
 */
@Path("/movies")
public class MovieResource {
    private EntityManager em = Utils.getEm();

    @GET
    @Produces("application/json")
    public List<Movie> getMoviesAsJSON(@QueryParam("page") Integer page,
                                       @QueryParam("items") Integer items) {
        TypedQuery<Movie> q = em.createNamedQuery("AllMovies", Movie.class);
        List<Movie> movies = q.getResultList();

        if (items == null || page == null ||
                items <= 0 || items >= movies.size() ||
                page <= 0 || page > movies.size()/items + 1) {
            return movies;
        } else {
            // Pagination
            List<Movie> pMovies = new ArrayList<>(items);
            int p = items * (page - 1);
            int n = (p + items <= movies.size()) ? (p + items) : movies.size();
            pMovies.addAll(movies.subList(p, n));
            return pMovies;
        }
    }

    @GET
    @Produces("text/html")
    public Viewable getMoviesAsHtml() {
        final Map<String, Object> map = new HashMap<>();

        Query q = em.createNamedQuery("AllMovies");
        List<Movie> movies = q.getResultList();
        map.put("movies", movies);
        return new Viewable("/movies/movies.ftl", map);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getMovieById(@PathParam("id") int id) {
        Movie m = em.find(Movie.class, id);
        return Response.ok(m.buildJSONString(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/{id}")
    @Produces("text/html")
    public Viewable getMovieByIdAsHtml(@PathParam("id") int id) {
        final Map<String, Object> map = new HashMap<>();
        Movie m = em.find(Movie.class, id);
        map.put("movie", m);
        return new Viewable("/movies/movie.ftl", map);
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie m) {
        return Response.ok(m.getTitle(), MediaType.APPLICATION_JSON_TYPE).build();
    }
}
