<%--
  Created by IntelliJ IDEA.
  User: Sketchy
  Date: 10.12.2014
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorize</title>
<%--
    <link rel="stylesheet" href="css/style.css"/>
--%>
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
  <form class="form-container" action="http://localhost:8080/rsoi2/rs/oauth/getallow" method="post">
    <div class="form-title"><h2>Oauth authorize</h2></div>
    <div class="form-title">Name</div>
    <input class="form-field" type="text" name="login" /><br />
    <div class="form-title">Password</div>
    <input class="form-field" type="password" name="password" /><br />

    <input type="hidden" name="clientid" value="<%=request.getParameter("clientid")%>">
    <input type="hidden" name="redirect" value="<%=request.getParameter("redirect")%>">
    <input type="hidden" name="state" value="<%=request.getParameter("state")%>">

    <div class="submit-container">
      <input class="submit-button" type="submit" value="Login" />
      <a href="signup.jsp"><input class="submit-button" type="button" value="Register"/></a>
    </div>
  </form>
</body>
</html>
