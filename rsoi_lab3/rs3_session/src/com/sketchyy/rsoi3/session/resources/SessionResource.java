package com.sketchyy.rsoi3.session.resources;

import com.sketchyy.rsoi3.actors.entities.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Sketchy on 09.01.2015.
 */
@Path("/")
public class SessionResource {
    private static Logger l = Logger.getLogger(SessionResource.class);
    private EntityManager em = Persistence.createEntityManagerFactory("session-unit").createEntityManager();

    @POST
    @Path("/authorize")
    public Response authorize(@HeaderParam("username") String username,
                              @HeaderParam("password") String password) {
        l.warn("SESSION + /rs3ses/authorize");
        TypedQuery<User> tq = em.createQuery("select U from User u where u.username = :name and u.password = :pass", User.class);
        tq.setParameter("name", username);
        tq.setParameter("pass", password);

        List<User> users = tq.getResultList();
        if (users.size() == 0) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            return Response.ok().build();
        }
    }

    @POST
    @Path("/checkuser")
    public Response checkUser( @HeaderParam("username") String username) {
        l.warn("SESSION + /rs3ses/checkuser");
        TypedQuery<User> tq = em.createQuery("select U from User u where u.username = :name", User.class);
        tq.setParameter("name", username);

        List<User> users = tq.getResultList();
        if (users.size() == 0) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            return Response.ok().build();
        }
    }

    @POST
    @Path("/register")
    public Response register(@HeaderParam("name") String name,
                             @HeaderParam("email") String email,
                             @HeaderParam("username") String username,
                             @HeaderParam("password") String password) {
        l.warn("SESSION + /rs3ses/register");
        TypedQuery<User> tq = em.createQuery("select U from User u where u.username = :username", User.class);
        tq.setParameter("username", username);
        List<User> users = tq.getResultList();
        if (users.size() > 0 ) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        User u = new User(name, email, username, password);
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        return Response.ok().build();
    }


}
