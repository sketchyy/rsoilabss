<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ooops</title>
<link rel="stylesheet" href="css/bootstrap.css"/>
<link rel="stylesheet" href="css/bootstrap-responsive.css"/>
<script src="js/bootstrap.min.js"></script>
<style>
.center {
	position: absolute;
    height: 500px;
    width: 500px;
    margin: -250px 0 0 -250px;
    top: 50%;
    left: 50%;
}
</style>
</head>
<body background="img/background.jpg">
	<div class="message">
		<h1>Error occurred!</h1>
		<div class="lead center" style="text-align: center; color: red;">
			<%=request.getAttribute("errmessage")%>
			<br>
			<form action="index.jsp" method="GET">
				<input type="submit" class="btn btn-danger btn-large" value="Back"/>
			</form>
		</div>
	</div>
</body>
</html>