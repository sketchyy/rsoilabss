<html>
<head>
    <title>Actors!</title>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="css/bootstrap-responsive.css"/>
    <script src="js/bootstrap.min.js"></script>
</head>

<body>
<h1>Actors</h1>

<table class="menu-bar">
    <tr>
        <td><a href="/rsoi2/rs/me">Me</a></td>
        <td><a href="/rsoi2/rs/movies">Movies</a></td>
        <td><a href="/rsoi2/rs/actors">Actors</a></td>
        <td><a href="/rsoi2/rs/status">Status</a></td>
        <td><a href="/rsoi2/logout">Logout</a></td>
    </tr>
</table>
Name: ${actor.getName()} <br>
Height: ${actor.getHeight()} <br>
Country: ${actor.getCountry()} <br>
Gender: ${actor.getGender()} <br>
<br><br>
<#list actor.getMovies() as m>
<tr>
    <td>${m.getMovieId()}</td>
    <td><a href="/rsoi2/rs/movies/${m.getMovieId()}">${m.getTitle()}</a></td>
    <td>${m.getMoney()}</td>
    <td>${m.getYear()}</td>
    <td>${m.getCountry()}</td>
    <td>${m.getTaglines()}</td>
</tr>
</#list>
</body>
</html>