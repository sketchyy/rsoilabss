<%@ page import="com.sketchyy.rsoi3.movies.entities.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sketchyy.rsoi3.actors.entities.Actor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Actors</h1>

<%=request.getSession().getAttribute("user")%>

<table class="menu-bar">
    <tr>
        <td><a href="/rs3/movies?p=1&q=2">Movies</a></td>
        <td><a href="/rs3/actors">Actors</a></td>
        <td><a href="/rs3/status">Status</a></td>
        <td><a href="/rs3/logout">Logout</a></td>
    </tr>
</table>
<br>
<a href="/rs3/actorcreate.jsp">Create actor</a>
<br><br>
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
    %>
</table>
</body>
</html>
