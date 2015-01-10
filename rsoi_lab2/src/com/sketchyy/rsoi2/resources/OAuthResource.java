package com.sketchyy.rsoi2.resources;

import com.sketchyy.rsoi2.entities.Allow;
import com.sketchyy.rsoi2.entities.Code;
import com.sketchyy.rsoi2.entities.Token;
import com.sketchyy.rsoi2.entities.User;
import com.sketchyy.rsoi2.utils.Utils;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sketchy on 10.12.2014.
 */
@Path("/oauth")
public class OAuthResource {
    private static EntityManager em = Utils.getEm();

    @GET
    @Path("/authorize")
    public Response getAuthCode(@QueryParam("clientid") String clientId,
                                @QueryParam("redirect") String redirectUri,
                                @QueryParam("state") String state) {
        // TODO check at DB
        if (!isRegisteredApp(clientId)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Utils.buildJSONError("Client is not exist!")).build();
        }


        UriBuilder uri = UriBuilder.fromUri("http://localhost:8080/rsoi2/ologin.jsp")
                .queryParam("clientid", clientId)
                .queryParam("redirect", redirectUri)
                .queryParam("state", state);

        return Response.temporaryRedirect(uri.build()).build();
    }

    @POST
    @Path("/getallow")
    public Response getAllow(@FormParam("login") String login,
                             @FormParam("password") String password,
                             @FormParam("clientid") String client,
                             @FormParam("redirect") String redirectUri,
                             @FormParam("state") String state,
                             @Context HttpServletRequest req) {
        // 1. authoize login/pass
        UriBuilder uriBuilder;
        if (Utils.authorize(login, password) == null) {
            req.getSession().setAttribute("error", "1");
            uriBuilder = UriBuilder.fromUri("http://localhost:8080/rsoi2/ologin.jsp");
            return Response.temporaryRedirect(uriBuilder.build()).build();
        }

        // check allow
        if (checkAllow(login, client)) {
            // Allow already given, send code
            uriBuilder = UriBuilder.fromUri("http://localhost:8080/rsoi2/rs/oauth/sendcode");
        } else {
            // Allow not given yet, redirect to give allow
            uriBuilder = UriBuilder.fromUri("http://localhost:8080/rsoi2/allow.jsp");
        }
        return Response.temporaryRedirect(uriBuilder.build()).build();
    }


    @POST
    @Path("/sendcode")
    public Response sendCode(@FormParam("login") String login,
                             @FormParam("clientid") String client,
                             @FormParam("redirect") String redirectUri,
                             @FormParam("state") String state
    ) {
        // If authorized, then write Allow to DB
        if (!checkAllow(login, client)) {
            saveAllow(login, client);
        }

        // 2. generate code
        String authCode = generateCode(login);

        UriBuilder uriBuilder = UriBuilder.fromUri(redirectUri)
                .queryParam("code", authCode)
                .queryParam("state", state);

        return Response.temporaryRedirect(uriBuilder.build()).build();

    }

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@FormParam("clientid") String clientid,
                             @FormParam("secret") String secret,
                             @FormParam("code") String codeStr,
                             @FormParam("refresh_token") String refresh) {
        // 1. Check app
        if (!isRegisteredApp(clientid, secret)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Utils.buildJSONError("Application is not registered!")).build();
        }

        // 2. Refresh?
        if (refresh != null) {
            return Response.ok(refreshToken(refresh, rrr), MediaType.APPLICATION_JSON_TYPE).build();
        }

        // 3. Get code
        TypedQuery<Code> tq = em.createQuery("select c from Code c where c.code = :code", Code.class);
        tq.setParameter("code", codeStr);
        if (tq.getResultList().size() == 0) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Utils.buildJSONError("Authorization code does not exists!")).build();
        }
        Code code = tq.getResultList().get(0);

        // 4. Is code actual?
        Timestamp now = new Timestamp(new Date().getTime());
        if (code.getExpiresIn().before(now)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Utils.buildJSONError("Code is expired!")).build();
        }

        // 5. generate token
        JSONObject tokenJSON = generateToken(code);
        return Response.ok(tokenJSON.toString(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    public static String generateCode(String username) {
        // get user by username
        TypedQuery<User> tq = em.createQuery("select u from User u where u.username = :username", User.class);
        tq.setParameter("username", username);
        User u = tq.getResultList().get(0);

        // generate code
        String authCode = generateNumberSequence(15);

        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MINUTE, 10);
        Code code = new Code(u.getUserId(), authCode, new Timestamp(cl.getTime().getTime()));

        // write to db
        em.getTransaction().begin();
        em.persist(code);
        em.getTransaction().commit();

        return code.getCode();
    }

    public static JSONObject generateToken(Code code) {
        // generate token
        String tokenSb = generateNumberSequence(20);

        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_YEAR, 180);
        Token token = new Token(code.getUserId(), tokenSb.toString(), new Timestamp(cl.getTime().getTime()));

        // write to db
        em.getTransaction().begin();
        em.persist(token);
        em.getTransaction().commit();


        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", token.getToken());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        tokenJson.put("expires_in", df.format(token.getExpiresIn().getTime()));
        tokenJson.put("type", "bearer");

        return tokenJson;
    }

    private static String refreshToken(String token, String refresh) {

        // get token with this code and user id
        TypedQuery<Token> tq = em.createQuery("select t from Token t where t.token = :tok", Token.class);
        tq.setParameter("tok", token);


        if (!refrtoken.equals(refresh)) {
            return Utils.buildJSONError("Invalid refresh token");
        }
        // Check existence
        if (tq.getResultList().size() == 0) {
            return Utils.buildJSONError("Token not found");
        }
        Token t = tq.getResultList().get(0);

        // check expired
        Timestamp now = new Timestamp(new Date().getTime());
        if (t.getExpiresIn().after(now)) {
            return Utils.buildJSONError("Token is not expired");
        }

        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_YEAR, 180);
        t.setExpiresIn(new Timestamp(cl.getTime().getTime()));

        // write to db
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();

        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", t.getToken());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        tokenJson.put("expires_in", df.format(t.getExpiresIn().getTime()));

        return tokenJson.toJSONString();
    }

    public static boolean isRegisteredApp(String clientId, String secret) {
        Query q = em.createQuery("select count(a) from App a where a.clientId = :id and a.secret = :secret");
        q.setParameter("id", clientId);
        q.setParameter("secret", secret);
        return ((Number) q.getSingleResult()).intValue() > 0;
    }

    public static boolean isRegisteredApp(String clientId) {
        Query q = em.createQuery("select count(a) from App a where a.clientId = :id");
        q.setParameter("id", clientId);
        return ((Number) q.getSingleResult()).intValue() > 0;
    }

    private static String generateNumberSequence(int n) {
        Random rnd = new Random();
        Query q;
        StringBuilder authCode;
        do {
            authCode = new StringBuilder();
            for (int i = 0; i < n; i++) {
                authCode.append(Integer.toString(rnd.nextInt(10)));
            }

            q = em.createQuery("select count(c) from Code c where c.code = :code");
            q.setParameter("code", authCode.toString());
        } while (((Number) q.getSingleResult()).intValue() > 0);
        return authCode.toString();
    }

    private static void saveAllow(String login, String client) {
        TypedQuery<Integer> tq = em.createQuery("select u.userId from User u where u.username = :l", Integer.class);
        tq.setParameter("l", login);
        int userId = tq.getSingleResult();

        Allow a = new Allow(userId, client, "y");

        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
    }

    private static boolean checkAllow(String login, String client) {
        TypedQuery<Allow> atq = em.createQuery("select a from Allow a where a.clientId = :cl and a.userId = " +
                "(select u.userId from User u where u.username = :uid)", Allow.class);
        atq.setParameter("cl", client);
        atq.setParameter("uid", login);

        if (atq.getResultList().size() == 0) {
            return false;
        }

        if (!"y".equals(atq.getResultList().get(0).getIsAllowed())) {
            return false;
        }
        return true;
    }

    private static String refrtoken = "91238791823";
    private static String rrr = refrtoken;
}
