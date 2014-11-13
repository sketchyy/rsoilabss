<%@page import="com.sketchyy.rsoi1.oauth.PropsHolder"%>
<%@page import="org.apache.http.client.utils.URIBuilder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RSOI1</title>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/bootstrap-responsive.css"/>
<script src="js/bootstrap.min.js"></script>
<style>
.center {
	position: absolute;
    height: 50px;
    width: 200px;
    margin: -50px 0 0 -100px;
    top: 50%;
    left: 50%;
}
</style>
</head>
<body background="img/background.jpg">
	<form class="center" action="<%= PropsHolder.getProperties().getProperty("authorize_uri") %>" method="GET">
		<h1>LAB # 1</h1>
		<input type="hidden" name="client_id" value="<%= PropsHolder.getProperties().getProperty("client_id") %>"/>
		<input type="hidden" name="scope" value="wow.profile"/>
		<input type="hidden" name="state" value="test_state"/>
		<input type="hidden" name="redirect_uri" value="<%= PropsHolder.getProperties().getProperty("redirect_uri") %>"/>
		<input type="hidden" name="response_type" value="code"/>
		<input type="submit" class="btn btn-large btn-primary" value="Battle.net authorize"/>
	</form>
</body>
</html>