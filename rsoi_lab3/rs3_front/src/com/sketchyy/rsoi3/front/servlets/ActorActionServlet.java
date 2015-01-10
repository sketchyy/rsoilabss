package com.sketchyy.rsoi3.front.servlets;

import com.sketchyy.rsoi3.actors.entities.Actor;
import com.sketchyy.rsoi3.movies.entities.Movie;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;

/**
 * Created by Sketchy on 10.01.2015.
 */
    @WebServlet(name = "ActorActionServlet", urlPatterns = "/actoraction")
public class ActorActionServlet extends HttpServlet {
    private String baseUri = "http://localhost:8080";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Actor a = new Actor();
        a.setActorId(Integer.parseInt(request.getParameter("actorId")));
        a.setName(request.getParameter("name"));
        a.setGender(request.getParameter("gender"));
        a.setCountry(request.getParameter("country"));
        a.setHeight(Integer.parseInt(request.getParameter("height")));

        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3act/actors/");

            HttpPut put = new HttpPut(uriBuilder.build());
            put.addHeader("content-type", "application/json");

            StringEntity str = new StringEntity(a.buildJSON().toJSONString());
            put.setEntity(str);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(put);

            response.sendRedirect("/rs3/actors");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }


    private void create(HttpServletRequest request, HttpServletResponse response) {
        Actor a = new Actor();
        a.setActorId(getMaxId());
        a.setName(request.getParameter("name"));
        a.setGender(request.getParameter("gender"));
        a.setCountry(request.getParameter("country"));
        a.setHeight(Integer.parseInt(request.getParameter("height")));

        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3act/actors");

            HttpPost post = new HttpPost(uriBuilder.build());
            post.addHeader("content-type", "application/json");

            StringEntity str = new StringEntity(a.buildJSON().toJSONString());
            post.setEntity(str);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == Response.Status.CONFLICT.getStatusCode()) {
                request.getSession().setAttribute("error", "already exists");
                response.sendRedirect("/rs3/moviecreate.jsp");
            } else {
                response.sendRedirect("/rs3/actors");
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3act/actors/" + request.getParameter("id"));

            HttpDelete delete = new HttpDelete(uriBuilder.build());

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(delete);

            response.sendRedirect("/rs3/actors");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private int getMaxId() {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUri);
            uriBuilder.setPath("/rs3act/actors/maxid");

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
