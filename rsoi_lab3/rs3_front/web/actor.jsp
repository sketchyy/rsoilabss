<%@ page import="com.sketchyy.rsoi3.movies.entities.Movie" %>
<%@ page import="com.sketchyy.rsoi3.actors.entities.Actor" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Sketchy
  Date: 09.01.2015
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actor</title>
</head>
<body>
<%
    Actor a = (Actor) request.getAttribute("actor");
    if (a == null) {
        response.sendRedirect("http://localhost:8080/rs3/movies");
        return;
    }
%>
<h1>Actor</h1>
<table class="menu-bar">
    <tr>
        <td><a href="/rs3/movies?p=1&q=2">Movies</a></td>
        <td><a href="/rs3/actors">Actors</a></td>
        <td><a href="/rs3/status">Status</a></td>
        <td><a href="/rs3/logout">Logout</a></td>
    </tr>
</table>
<br><br>
Name: <%=a.getName()%> <br>
Country: <%=a.getCountry()%> <br>
Gender: <%=a.getGender()%> <br>
Height: <%=a.getHeight()%> <br>

<br>

<h2>Movies:</h2>
<table class="table-bordered">
    <thead>
    <tr>
        <th>#</th>
        <th>Title</th>
        <th>Budget</th>
        <th>Year</th>
        <th>Country</th>
        <th>Tagline</th>
    </tr>
    </thead>
    <%
        List<Movie> movs = (List<Movie>) request.getAttribute("movies");
        if (movs != null) {
            for (Movie m : movs) {
    %>
    <tr>
        <td><%=m.getMovieId()%>
        </td>
        <td><a href="/rs3/movies?id=<%=m.getMovieId()%>"><%=m.getTitle()%>
        </a></td>
        <td><%=m.getMoney()%>
        </td>
        <td><%=m.getYear()%>
        </td>
        <td><%=m.getCountry()%>
        </td>
        <td><%=m.getTaglines()%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<br><br>
<table class="menu-bar">
    <tr>
        <td>
            <form method="get" action="actoredit.jsp">
                <input type="hidden" name="actorId" value="<%=a.getActorId()%>">
                <input type="hidden" name="name" value="<%=a.getName()%>">
                <input type="hidden" name="country" value="<%=a.getCountry()%>">
                <input type="hidden" name="gender" value="<%=a.getGender()%>">
                <input type="hidden" name="height" value="<%=a.getHeight()%>">
                <input class="submit-button" type="submit" value="Edit"/>
                <a href="/rs3/actoraction?act=delete&id=<%=a.getActorId()%>" onclick="confirm('Remove actor?')"><input
                        class="btn" type="button" value="Remove"/></a>
            </form>
        </td>
    </tr>
</table>

</body>
</html>
