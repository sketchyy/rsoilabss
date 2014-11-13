<%@page import="com.sketchyy.rsoi1.oauth.Character"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.http.client.utils.URIBuilder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RSOI1 MAIN</title>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/bootstrap-responsive.css"/>
<script src="js/bootstrap.min.js"></script>
<style>
.center {
	position: absolute;
    width: 800px;
    margin: -190px 0 0 -400px;
    top: 50%;
    left: 50%;
    background-color: #f9f9f9;
}
</style>
</head>
<body background="img/background.jpg">
	<h1>Characters list:</h1>	
	<table class="center table table-bordered table-hover table-condensed">
	<thead>
		<tr>
			<th>#</th>
			<th>Icon</th>
			<th>Name</th>
			<th>Level</th>
			<th>Guild</th>
			<th>Realm</th>
		</tr>
	</thead>
	<%
		@SuppressWarnings("unchecked")
		List<Character> characters = (List<Character>)request.getAttribute("characters");	
		
		for (int i = 0; i < characters.size(); i++) {
			Character c = characters.get(i);
	%>	
		<tr>
			<td><%= i+1  %></td>
			<td style="text-align:center;"><img src="<%= c.getThumbnail()  %>"/></td>
			<td><%= c.getName()  %></td>
			<td><%= c.getLevel() %></td>
			<td><%= c.getGuild() %></td>
			<td><%= c.getRealm() %></td>
		</tr>
	<% 			
		}
	%>
	</table>
	<form style="text-align:center;" action="index.jsp" method="GET">
		<input type="submit" class="btn btn-danger btn-large" value="Back" />
	</form>
</body>
</html>