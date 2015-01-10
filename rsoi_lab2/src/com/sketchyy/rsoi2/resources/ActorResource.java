package com.sketchyy.rsoi2.resources;

import com.sketchyy.rsoi2.entities.Actor;
import com.sketchyy.rsoi2.entities.Movie;
import com.sketchyy.rsoi2.utils.Utils;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
@Path("/actors")
public class ActorResource {
    private EntityManager em = Utils.getEm();

    @GET
    @Produces("text/html")
    public Viewable getActorsAsHtml() {
        final Map<String, Object> map = new HashMap<>();

        Query q = em.createNamedQuery("AllActors");
        List<Movie> actors = q.getResultList();
        map.put("actors", actors);

        return new Viewable("/actors/actors.ftl", map);
    }
    @GET
    @Produces("application/json")
    public List<Actor> getActorsAsJson(@QueryParam("page") Integer page,
                                    @QueryParam("items") Integer items) {
        Query q = em.createNamedQuery("AllActors");
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
    @Produces("text/html")
    public Viewable getActorByIdAsHtml(@PathParam("id") int id) {
        final Map<String, Object> map = new HashMap<>();
        Actor actor = em.find(Actor.class, id);
        map.put("actor", actor);
        return new Viewable("/actors/actor.ftl", map);
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getActorById(@PathParam("id") int id) {
        Actor actor = em.find(Actor.class, id);
        return Response.ok(actor.buildJSONString(), MediaType.APPLICATION_JSON_TYPE).build();
    }
}
