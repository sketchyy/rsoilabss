<%@ page import="com.sketchyy.rsoi3.movies.entities.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Movies</h1>
<table class="menu-bar">
    <tr>
        <td><a href="/rs3/movies?p=1&q=2">Movies</a></td>
        <td><a href="/rs3/actors">Actors</a></td>
        <td><a href="/rs3/status">Status</a></td>
        <td><a href="/rs3/logout">Logout</a></td>
    </tr>
</table>
<br>
<a href="/rs3/moviecreate.jsp">Create movie</a>
<br><br>
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
    %>
</table>
<br>

<table class="table-bordered">
    <%
        List<Integer> pages = (List<Integer>) request.getAttribute("pages");
        if (pages != null) {
            for (int i = 0; i < pages.size(); i++) {
                int p = pages.get(i);
    %>
    <td><a href="/rs3/movies?p=<%=p%>&q=<%=request.getParameter("q")%>"> <%=p%></a></td>
    <%
            }
        }
    %>
</table>

</body>
</html>
</a>