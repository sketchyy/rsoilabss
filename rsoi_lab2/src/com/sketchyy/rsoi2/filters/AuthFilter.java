package com.sketchyy.rsoi2.filters;

import com.sketchyy.rsoi2.utils.Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sketchy on 13.12.2014.
 */
public class AuthFilter implements Filter {
    private List<String> excludeUris;

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

        // 1. Check userid
        String accept = httpReq.getHeader("Accept");

        if (accept.toLowerCase().contains("text/html")) {
            String userId = (String) session.getAttribute("user");
            if (userId == null) {
                session.setAttribute("error", httpReq.getRequestURI());
                httpResp.sendRedirect("/rsoi2/index.jsp");
                return;
            }
        }

        // 2. Check token
        if (accept.toLowerCase().contains("application/json")) {
//            String tokenStr = httpReq.getHeader("token");
            String tokenStr = httpReq.getHeader("Authorization");

            if (!tokenStr.toLowerCase().contains("bearer")) {
                httpResp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpResp.setContentType("application/json");
                httpResp.getWriter().print(Utils.buildJSONError("No bearer"));
                return;
            }

            String[] tokens = tokenStr.split(" ");

            String tokenStatus = Utils.checkToken(tokens[1]);
            if (!"".equals(tokenStatus)) {
                httpResp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpResp.setContentType("application/json");
                httpResp.getWriter().print(tokenStatus);
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        excludeUris = new ArrayList<>(10);
        excludeUris.add("/rsoi2/rs/oauth/authorize");
        excludeUris.add("/rsoi2/rs/oauth/getallow");
        excludeUris.add("/rsoi2/rs/oauth/sendcode");
        excludeUris.add("/rsoi2/rs/oauth/authorize");
        excludeUris.add("/rsoi2/rs/oauth/token");
        excludeUris.add("/rsoi2/rs/status");
    }

}
