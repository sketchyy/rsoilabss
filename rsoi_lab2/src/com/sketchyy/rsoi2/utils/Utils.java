package com.sketchyy.rsoi2.utils;

import com.sketchyy.rsoi2.entities.Token;
import com.sketchyy.rsoi2.entities.User;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Sketchy on 11.12.2014.
 */
public class Utils {
    private static EntityManager em = Persistence.createEntityManagerFactory("rsoi2unit").createEntityManager();

    public static Integer authorize(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        // Check db
        TypedQuery<User> q = getEm().createQuery("select u from User u " +
                "where u.username = :name " +
                "and u.password = :password", User.class);

        q.setParameter("name", username);
        q.setParameter("password", password);

        List<User> users = q.getResultList();
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0).getUserId();
        }
    }

    public static String checkToken(String token) {
        TypedQuery<Token> tq = getEm().createQuery("select t from Token t where t.token = :token", Token.class);
        tq.setParameter("token", token);

        if (tq.getResultList().size() == 0) {
            return buildJSONError("Invalid token"); // no token in db
        }

        Token t = tq.getResultList().get(0);

        Timestamp now = new Timestamp(new Date().getTime());
        if (t.getExpiresIn().before(now)) {
            return buildJSONError("Token is expired");
        }

        return "";
    }

    public static String buildJSONError(String message) {
        JSONObject json = new JSONObject();
        json.put("error", message);
        return json.toJSONString();
    }

    public static EntityManager getEm() {
        return em;
    }

}
