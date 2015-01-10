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
    <title>Edit movie</title>
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
<form class="form-container" method="post" action="/rs3/movieaction?act=update">
    <div class="form-title"><h2>Edit movie</h2></div>

    <input class="form-field" type="hidden" name="movieId" value="<%=request.getParameter("movieId")%>"/><br/>

    <div class="form-title">Title</div>
    <input class="form-field" type="text" name="title" value="<%=request.getParameter("title")%>"/><br/>

    <div class="form-title">Genre</div>
    <input class="form-field" type="text" name="genre" value="<%=request.getParameter("genre")%>"/><br/>

    <div class="form-title">Year</div>
    <input class="form-field" type="text" name="year" value="<%=request.getParameter("year")%>"/><br/>

    <div class="form-title">Money</div>
    <input class="form-field" type="text" name="money" value="<%=request.getParameter("money")%>"/><br/>

    <div class="form-title">Taglines</div>
    <input class="form-field" type="text" name="taglines" value="<%=request.getParameter("taglines")%>"/><br/>

    <div class="form-title">Country</div>
    <input class="form-field" type="text" name="country" value="<%=request.getParameter("country")%>"/><br/>

    <div class="submit-container">
        <input class="submit-button" type="submit" value="Edit"/>
        <a href="movies"><input class="btn" type="button" value="Movies"/></a>
    </div>
</form>
</body>
</html>
