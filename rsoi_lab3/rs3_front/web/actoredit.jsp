<%@ page import="com.sketchyy.rsoi3.movies.entities.Movie" %>
<%--
  Created by IntelliJ IDEA.
  User: Sketchy
  Date: 10.01.2015
  Time: 0:35+------
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit actor</title>
  <%--
      <link rel="stylesheet" href="css/style.css"/><link >
  --%>
</head>
<body>
<%
  String error = (String) session.getAttribute("error");
  if (error != null) {
    session.removeAttribute("error");
%>
<div class="error"><h2>Please login! <%=error%>
</h2></div>
<%
  }
%>
<form class="form-container" method="post" action="/rs3/actoraction?act=update">
  <div class="form-title"><h2>Edit actor</h2></div>

  <input class="form-field" type="hidden" name="actorId" value="<%=request.getParameter("actorId")%>"/><br/>

  <div class="form-title">Name</div>
  <input class="form-field" type="text" name="name" value="<%=request.getParameter("name")%>"/><br/>

  <div class="form-title">Country</div>
  <input class="form-field" type="text" name="country" value="<%=request.getParameter("country")%>"/><br/>

  <div class="form-title">Gender</div>
  <input class="form-field" type="text" name="gender" value="<%=request.getParameter("gender")%>"/><br/>

  <div class="form-title">Height</div>
  <input class="form-field" type="text" name="height" value="<%=request.getParameter("height")%>"/><br/>

  <div class="submit-container">
    <input class="submit-button" type="submit" value="Edit"/>
    <a href="actors"><input class="btn" type="button" value="Actors"/></a>
  </div>
</form>
</body>
</html>
