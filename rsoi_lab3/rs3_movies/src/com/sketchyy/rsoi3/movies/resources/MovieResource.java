package com.sketchyy.rsoi3.movies.resources;


import com.sketchyy.rsoi3.movies.entities.Movie;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sketchy on 14.12.2014.
 */
@Path("/movies")
public class MovieResource {
    private static Logger l = Logger.getLogger(MovieResource.class);
    private EntityManager em = Persistence.createEntityManagerFactory("mov-unit").createEntityManager();

    @GET
    @Produces("application/json")
    public List<Movie> getMoviesAsJSON(@QueryParam("page") Integer page,
                                       @QueryParam("items") Integer items) {
        l.warn("MOVIES + /rs3mov/movies");
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
    @Path("/{id}")
    @Produces("application/json")
    public Response getMovieById(@PathParam("id") int id) {
        l.warn("MOVIES + /rs3mov/movies/" + id);
        Movie m = em.find(Movie.class, id);
        return Response.ok(m, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Consumes("application/json")
    public Response createMovie(Movie m) {
        l.warn("MOVIES + /rs3mov/movies");
        if (em.find(Movie.class, m.getMovieId()) != null) {
            return Response.status(Response.Status.CONFLICT).entity("Resource already exists").build();
        }
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes("application/json")
    public Response updateMovie(Movie m) {
        l.warn("MOVIES + /rs3mov/movies");
        if (em.find(Movie.class, m.getMovieId()) == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Resource not found").build();
        }
        em.getTransaction().begin();
        em.merge(m);
        em.getTransaction().commit();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("text/plain")
    public Response deleteMovieById(@PathParam("id") int id) {
        l.warn("MOVIES + /rs3mov/movies/" + id);
        Movie m = em.find(Movie.class, id);
        if (m == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Resource not found").build();
        }
        em.getTransaction().begin();
        em.remove(m);
        em.getTransaction().commit();
        return Response.ok().build();
    }

    @GET
    @Path("/byactor")
    @Produces("application/json")
    public List<Movie> getActorsByMovie(@QueryParam("id") Integer id) {
        l.warn("MOVIES + /rs3mov/movies/byactor?id=" + id);
        if (id == null) {
            return new ArrayList<>();
        }
        TypedQuery<Movie> tq = em.createQuery("select m from Movie m where m.movieId in" +
                " (select r.movieId from ActorsMovies r where r.actorId = :actor)", Movie.class);
        tq.setParameter("actor", id);
        List<Movie> movies = tq.getResultList();
        return movies;
    }

    @GET
    @Path("/count")
    public int getMoviesCount() {
        l.warn("MOVIES + /rs3mov/movies/count");
        Query q = em.createQuery("select count(m.movieId) from Movie m");
        return ((Number)q.getSingleResult()).intValue() + 1;
    }

    @GET
    @Path("/maxid")
    public int getNewId() {
        l.warn("MOVIES + /rs3mov/movies/maxid");
        Query q = em.createQuery("select max(m.movieId) from Movie m");
        return ((Number)q.getSingleResult()).intValue() + 1;
    }
}
