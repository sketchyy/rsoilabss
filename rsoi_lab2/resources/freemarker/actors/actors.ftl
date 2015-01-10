<html>
<head>
    <title>Welcome!</title>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="css/bootstrap-responsive.css"/>
    <script src="js/bootstrap.min.js"></script>
</head>

<body>
<h1>Movies</h1>

<table class="menu-bar">
    <tr>
        <td><a href="/rsoi2/rs/me">Me</a></td>
        <td><a href="/rsoi2/rs/movies">Movies</a></td>
        <td><a href="/rsoi2/rs/actors">Actors</a></td>
        <td><a href="/rsoi2/rs/status">Status</a></td>
        <td><a href="/rsoi2/logout">Logout</a></td>
    </tr>
</table>

<table class="table-bordered">
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Height</th>
        <th>Country</th>
        <th>Gender</th>
    </tr>
    </thead>
<#list actors as a>
    <tr>
        <td>${a.getActorId()}</td>
        <td><a href="/rsoi2/rs/actors/${a.getActorId()}">${a.getName()}</a></td>
        <td>${a.getHeight()}</td>
        <td>${a.getCountry()}</td>
        <td>${a.getGender()}</td>
    </tr>
</#list>
</table>
</body>
</html>