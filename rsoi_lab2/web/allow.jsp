<%--
  Created by IntelliJ IDEA.
  User: Sketchy
  Date: 18.12.2014
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Allow access?</title>
    <link rel="stylesheet" href="css/style.css"/><link >
</head>
<body>
  <form class="form-container" action="http://localhost:8080/rsoi2/rs/oauth/sendcode" method="post">
    <div class="form-title"><h2>Allow access to private data?</h2></div>

    <input type="hidden" name="login" value="<%=request.getParameter("login")%>">
    <input type="hidden" name="clientid" value="<%=request.getParameter("clientid")%>">
    <input type="hidden" name="redirect" value="<%=request.getParameter("redirect")%>">
    <input type="hidden" name="state" value="<%=request.getParameter("state")%>">

    <div class="submit-container">
      <input class="submit-button" type="submit" value="Yes" />
      <a href="index.jsp">
        <input class="submit-button" type="button" value="No"/>
      </a>
    </div>
  </form>
</body>
</html>
