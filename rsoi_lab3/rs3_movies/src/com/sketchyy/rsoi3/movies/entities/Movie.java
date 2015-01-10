package com.sketchyy.rsoi3.movies.entities;

import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by Sketchy on 01.01.2015.
 */
@Entity
@Table(name = "RSOI2_MOVIES", schema = "SKETCHYY", catalog = "")
@NamedQuery(name = "AllMovies", query = "select m from Movie m order by m.movieId")
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int year;
    private int money;
    private String taglines;
    private String country;

    public Movie(String title, String genre, int year, int money, String taglines, String country) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.money = money;
        this.taglines = taglines;
        this.country = country;
    }

    public Movie() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieSeq")
    @SequenceGenerator(name="movieSeq", sequenceName="MOVIES_SEQ", allocationSize=1)
    @Column(name = "MOVIE_ID")
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "GENRE")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "YEAR")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "MONEY")
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Basic
    @Column(name = "TAGLINES")
    public String getTaglines() {
        return taglines;
    }

    public void setTaglines(String taglines) {
        this.taglines = taglines;
    }

    @Basic
    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (money != movie.money) return false;
        if (movieId != movie.movieId) return false;
        if (year != movie.year) return false;
        if (country != null ? !country.equals(movie.country) : movie.country != null) return false;
        if (genre != null ? !genre.equals(movie.genre) : movie.genre != null) return false;
        if (taglines != null ? !taglines.equals(movie.taglines) : movie.taglines != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movieId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + money;
        result = 31 * result + (taglines != null ? taglines.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    public JSONObject buildJSON() {
        JSONObject json = new JSONObject();

        json.put("movieId", movieId);
        json.put("title", title);
        json.put("genre", genre);
        json.put("year", year);
        json.put("money", money);
        json.put("taglines", taglines);
        json.put("country", country);

        return json;
    }
}
