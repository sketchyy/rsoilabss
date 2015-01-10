package com.sketchyy.rsoi3.actors.Resource;

import com.sketchyy.rsoi3.actors.entities.Actor;
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
 * Created by Sketchy on 10.01.2015.
 */
@Path("/actors")
public class ActorResource {
    private static Logger l = Logger.getLogger(ActorResource.class);
    private EntityManager em = Persistence.createEntityManagerFactory("actor-unit").createEntityManager();

    @GET
    @Produces("application/json")
    public List<Actor> getActorsAsJSON(@QueryParam("page") Integer page,
                                       @QueryParam("items") Integer items) {
        l.warn("ACTORS + /rs3act/actors");
        TypedQuery<Actor> q = em.createNamedQuery("AllActors", Actor.class);
        List<Actor> actors = q.getResultList();

        if (items == null || page == null ||
                items <= 0 || items >= actors.size() ||
                page <= 0 || page > actors.size()/items + 1) {
            return actors;
        } else {
            // Pagination
            List<Actor> pActors = new ArrayList<>(items);
            int p = items * (page - 1);
            int n = (p + items <= actors.size()) ? (p + items) : actors.size();
            pActors.addAll(actors.subList(p, n));
            return pActors;
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getActorById(@PathParam("id") int id) {
        l.warn("ACTORS + /rs3act/actors/" + id);
        Actor a = em.find(Actor.class, id);
        return Response.ok(a, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Consumes("application/json")
    public Response createActor(Actor a) {
        l.warn("ACTORS + /rs3act/actors");
        if (em.find(Actor.class, a.getActorId()) != null) {
            return Response.status(Response.Status.CONFLICT).entity("Resource already exists").build();
        }
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes("application/json")
    public Response updateActor(Actor a) {
        l.warn("ACTORS + /rs3act/actors");
        if (em.find(Actor.class, a.getActorId()) == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Resource not found").build();
        }
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("text/plain")
    public Response deleteActorById(@PathParam("id") int id) {
        l.warn("ACTORS + /rs3act/actors/" + id);
        Actor a = em.find(Actor.class, id);
        if (a == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Resource not found").build();
        }
        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
        return Response.ok().build();
    }

    @GET
    @Path("/bymovie")
    @Produces("application/json")
    public List<Actor> getActorsByMovie(@QueryParam("id") Integer id) {
        l.warn("ACTORS + /rs3act/actors/bymovie?id=" + id);
        if (id == null) {
            return new ArrayList<>();
        }
        TypedQuery<Actor> tq = em.createQuery("select a from Actor a where a.actorId in" +
                " (select r.actorId from ActorsMovies r where r.movieId = :movie)", Actor.class);
        tq.setParameter("movie", id);
        List<Actor> actors = tq.getResultList();
        return actors;
    }

    @GET
    @Path("/count")
    public int getMoviesCount() {
        l.warn("ACTORS + /rs3act/actors/count");
        Query q = em.createQuery("select count(a.actorId) from Actor a");
        return ((Number)q.getSingleResult()).intValue() + 1;
    }

    @GET
    @Path("/maxid")
    public int getNewId() {
        l.warn("ACTORS + /rs3act/actors/maxid");
        Query q = em.createQuery("select max(a.actorId) from Actor a");
        return ((Number)q.getSingleResult()).intValue() + 1;
    }
}
