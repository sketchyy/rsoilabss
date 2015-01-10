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
              <th>Title</th>
              <th>Budget</th>
              <th>Year</th>
              <th>Country</th>
              <th>Tagline</th>
          </tr>
          </thead>
          <#list movies as m>
          <tr>
              <td>${m.getMovieId()}</td>
              <td><a href="/rsoi2/rs/movies/${m.getMovieId()}">${m.getTitle()}</a></td>
              <td>${m.getMoney()}</td>
              <td>${m.getYear()}</td>
              <td>${m.getCountry()}</td>
              <td>${m.getTaglines()}</td>
          </tr>
          </#list>
      </table>
    </body>
</html>