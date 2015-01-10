package com.sketchyy.rsoi3.front.servlets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sketchy on 09.01.2015.
 */
@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    private List<String> excludeUris;
    private String logicUri = "http://localhost:8080";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpSession session = httpReq.getSession();

        String uriStr = httpReq.getRequestURI().toLowerCase();
        if (excludeUris.contains(uriStr)) {
            chain.doFilter(req, resp);
            return;
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(logicUri);
            uriBuilder.setPath("/rs3ses/checkuser");

            HttpPost post = new HttpPost(uriBuilder.build());
            post.setHeader("username", (String) httpReq.getSession().getAttribute("user"));
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse = client.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                session.setAttribute("error", httpReq.getRequestURI() + "  filter " + httpReq.getSession().getAttribute("user") + httpResponse.getStatusLine().getStatusCode());
                httpResp.sendRedirect("/rs3/");
                return;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        excludeUris = new ArrayList<>(10);
        excludeUris.add("/rs3/");
        excludeUris.add("/rs3/index.jsp");
        excludeUris.add("/rs3/auth.jsp");
        excludeUris.add("/rs3/authorize");
        excludeUris.add("/rs3/register.jsp");
        excludeUris.add("/rs3/register");
        excludeUris.add("/rs3/status");
        excludeUris.add("/rs3/logout");
    }

}
