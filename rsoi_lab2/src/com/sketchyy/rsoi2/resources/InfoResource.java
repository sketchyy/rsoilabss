package com.sketchyy.rsoi2.resources;

import com.sketchyy.rsoi2.entities.User;
import com.sketchyy.rsoi2.utils.Utils;
import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sketchy on 14.12.2014.
 */

@Path("/")
public class InfoResource {
    private EntityManager em = Utils.getEm();

    @GET
    @Path("/me")
    @Produces("text/html")
    public Viewable getMeAsHtml(@Context HttpServletRequest req) {
        final Map<String, Object> map = new HashMap<>();
        int id = Integer.parseInt((String) req.getSession().getAttribute("user"));
        User u = em.find(User.class, id);
        map.put("user", u);
        return new Viewable("/me.ftl", map);
    }

    @GET
    @Path("/me")
    @Produces("application/json")
    public Response getMeAsJson(@QueryParam("token") String token) {
        int id = getUserByToken(token);
        User u = em.find(User.class, id);
        return Response.ok(u, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/status")
    @Produces("text/html")
    public Viewable getStatusAsHtml(@Context HttpServletRequest req) {
        final Map<String, Object> map = new HashMap<>();

        Query q1 = em.createQuery("select count(m) from Movie m");
        map.put("movies", ((Number)q1.getSingleResult()).intValue());
        Query q2 = em.createQuery("select count(a) from Actor a");
        map.put("actors", ((Number)q2.getSingleResult()).intValue());
        Query q3 = em.createQuery("select count(u) from User u");
        map.put("users", ((Number)q3.getSingleResult()).intValue());

        return new Viewable("/status.ftl", map);
    }

    @GET
    @Path("/status")
    @Produces("application/json")
    public Response getStatusAsJson(@QueryParam("token") String token) {
        final JSONObject json = new JSONObject();

        Query q1 = em.createQuery("select count(m) from Movie m");
        json.put("movies", ((Number) q1.getSingleResult()).intValue());
        Query q2 = em.createQuery("select count(a) from Actor a");
        json.put("movies", ((Number) q2.getSingleResult()).intValue());
        Query q3 = em.createQuery("select count(u) from User u");
        json.put("movies", ((Number) q3.getSingleResult()).intValue());

        return Response.ok(json.toString(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    private int getUserByToken(String token) {
        TypedQuery<Integer> tq = em.createQuery("select t.userId from Token t where t.token = :tok", Integer.class);
        tq.setParameter("tok", token);
        return ((Number)tq.getSingleResult()).intValue();
    }

}
