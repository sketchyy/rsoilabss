<html>
    <head>
      <title>Movie</title>
    </head>

    <body>
    <h1>Movie</h1>
    <table class="menu-bar">
        <tr>
            <td><a href="/rsoi2/rs/me">Me</a></td>
            <td><a href="/rsoi2/rs/movies">Movies</a></td>
            <td><a href="/rsoi2/rs/actors">Actors</a></td>
            <td><a href="/rsoi2/rs/status">Status</a></td>
            <td><a href="/rsoi2/logout">Logout</a></td>
        </tr>
    </table>
    Title: ${movie.getTitle()} <br>
    Tagline: ${movie.getTaglines()} <br>
    Year: ${movie.getYear()} <br>
    Money: ${movie.getMoney()} <br>
    Country: ${movie.getCountry()} <br>
    </body>
</html>