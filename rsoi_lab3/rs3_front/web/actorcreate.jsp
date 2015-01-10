<%--
  Created by IntelliJ IDEA.
  User: Sketchy
  Date: 10.01.2015
  Time: 0:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Create actor</title>
  <%--
      <link rel="stylesheet" href="css/style.css"/><link >
  --%>
</head>
<body>
<%
  String  error = (String) session.getAttribute("error");
  if (error != null) {
    session.removeAttribute("error");
%>
<div class="error"><h2>Please login! <%=error%></h2></div>
<%
  }
%>
<form class="form-container" method="post" action="/rs3/actoraction?act=create">
  <div class="form-title"><h2>New actor</h2></div>

  <div class="form-title">Name</div>
  <input class="form-field" type="text" name="name"/><br/>

  <div class="form-title">Country</div>
  <input class="form-field" type="text" name="country"/><br/>

  <div class="form-title">Gender(M/W)</div>
  <input class="form-field" type="text" name="gender"/><br/>

  <div class="form-title">Height</div>
  <input class="form-field" type="text" name="height"/><br/>

  <div class="submit-container">
    <input class="submit-button" type="submit" value="Create"/>
    <a href="actors"><input class="btn" type="button" value="Actors"/></a>
  </div>
</form>
</body>
</html>
