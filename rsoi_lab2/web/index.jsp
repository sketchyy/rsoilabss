<%--
  Created by IntelliJ IDEA.
  User: Sketchy
  Date: 07.12.2014
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Authorize</title>
      <link rel="stylesheet" href="/rs/css/bootstrap.css"/>
      <link rel="stylesheet" href="/rs/css/bootstrap-responsive.css"/>
      <script src="/rs/js/bootstrap.min.js"></script>
  </head>
  <body>
      <%
        String  error = (String) session.getAttribute("error");
        if (error != null) {
          session.removeAttribute("error");
      %>
      <div class="error"><h2>Please login!</h2></div>
      <%
        }
      %>
      <form class="form-search" action="/rsoi2/authorize" method="post">
        <div class="form-title"><h2>Sign in</h2></div>
        <div class="form-title">Name</div>
        <input class="form-field" type="text" name="username" /><br />
        <div class="form-title">Password</div>
        <input class="form-field" type="password" name="password" /><br />
        <div class="submit-container">
          <input class="btn" type="submit" value="Login" />
          <a href="signup.jsp"><input class="btn" type="button" value="Register"/></a>
        </div>
      </form>
  </body>
</html>
