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
  <title>Sign up</title>
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
<div class="error"><h2>Error: <%=error%></h2></div>
<%
  }
%>
<form class="form-container" method="post" action="/rs3/register">
  <div class="form-title"><h2>Sign up</h2></div>
  <div class="form-title">Name</div>
  <input class="form-field" type="text" name="name" /><br />
  <div class="form-title">Email</div>
  <input class="form-field" type="text" name="email" /><br />
  <div class="form-title">Username</div>
  <input class="form-field" type="text" name="username" /><br />
  <div class="form-title">Password</div>
  <input class="form-field" type="password" name="password" /><br />
  <div class="submit-container">
    <input class="submit-button" type="submit" value="Sign up" />
  </div>
</form>
</body>
</html>
