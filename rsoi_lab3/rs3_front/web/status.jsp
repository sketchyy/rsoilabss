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
  <title>Welcome!</title>
</head>

<body>
<h1>Status</h1>
<table class="menu-bar">
  <tr>
    <td><a href="/rs3/movies?p=1&q=2">Movies</a></td>
    <td><a href="/rs3/actors">Actors</a></td>
    <td><a href="/rs3/status">Status</a></td>
    <td><a href="/rs3/logout">Logout</a></td>
  </tr>
</table>
<br>
Movies count = <%=request.getAttribute("movies_count")%><br>
Actors count = <%=request.getAttribute("actors_count")%><br>
</body>
</html>
