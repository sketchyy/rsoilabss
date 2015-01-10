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
    <title>Movie</title>
</head>
<body>
<%
    Movie m = (Movie) request.getAttribute("movie");
    if (m == null) {
        response.sendRedirect("http://localhost:8080/rs3/movies?p=1&q=2");
        return;
    }
%>
<h1>Movie</h1>
<table class="menu-bar">
    <tr>
        <td><a href="/rs3/movies?p=1&q=2">Movies</a></td>
        <td><a href="/rs3/actors">Actors</a></td>
        <td><a href="/rs3/status">Status</a></td>
        <td><a href="/rs3/logout">Logout</a></td>
    </tr>
</table>
<br><br>
Title: <%=m.getTitle()%> <br>
Tagline: <%=m.getTaglines()%> <br>
Year: <%=m.getYear()%> <br>
Money: <%=m.getMoney()%> <br>
Country: <%=m.getCountry()%> <br>

<br>
<h2>Actors:</h2>
<table class="table-bordered">
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Country</th>
        <th>Gender</th>
        <th>Height</th>
    </tr>
    </thead>
    <%
        List<Actor> actors = (List<Actor>) request.getAttribute("actors");
        if (actors != null) {
            for (Actor a : actors) {
    %>
    <tr>
        <td><%=a.getActorId()%>
        </td>
        <td><a href="/rs3/actors?id=<%=a.getActorId()%>"><%=a.getName()%>
        </a></td>
        <td><%=a.getCountry()%>
        </td>
        <td><%=a.getGender()%>
        </td>
        <td><%=a.getHeight()%>
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
            <form method="get" action="movieedit.jsp">
                <input type="hidden" name="movieId" value="<%=m.getMovieId()%>">
                <input type="hidden" name="title" value="<%=m.getTitle()%>">
                <input type="hidden" name="taglines" value="<%=m.getTaglines()%>">
                <input type="hidden" name="year" value="<%=m.getYear()%>">
                <input type="hidden" name="money" value="<%=m.getMoney()%>">
                <input type="hidden" name="country" value="<%=m.getCountry()%>">
                <input type="hidden" name="genre" value="<%=m.getGenre()%>">
                <input class="submit-button" type="submit" value="Edit"/>
                <a href="/rs3/movieaction?act=delete&id=<%=m.getMovieId()%>" onclick="return confirm('Remove movie?')"><input
                        class="btn" type="button" value="Remove"/></a>
            </form>
        </td>
    </tr>
</table>

</body>
</html>
