package com.sketchyy.rsoi2.entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by Sketchy on 21.12.2014.
 */
@Entity
@Table(name = "RSOI2_MOVIES", schema = "SKETCHYY", catalog = "")
@NamedQuery(name = "AllMovies", query = "select m from Movie m")
@XmlRootElement
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int year;
    private int money;
    private String taglines;
    private String country;
    private List<Actor> actors;

    @Id
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


    @ManyToMany
    @JoinTable(name = "RSOI2_ACTORS_MOVIES", catalog = "", schema = "SKETCHYY", joinColumns = @JoinColumn(name = "MOVIE_ID", referencedColumnName = "MOVIE_ID", nullable = false), inverseJoinColumns = @JoinColumn(name = "ACTOR_ID", referencedColumnName = "ACTOR_ID", nullable = false))
    @XmlTransient
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String buildJSONString() {
        JSONObject json = new JSONObject();

        json.put("movieId", movieId);
        json.put("title", title);
        json.put("genre", genre);
        json.put("year", year);
        json.put("money", money);
        json.put("taglines", taglines);
        json.put("country", country);
        JSONArray actorsJson = new JSONArray();

        for (Actor a : actors) {
            JSONObject actorJson = new JSONObject();
            actorJson.put("actorId", a.getActorId());
            actorJson.put("name", a.getName());
            actorJson.put("country", a.getCountry());
            actorsJson.add(actorJson);
        }

        json.put("actors", actorsJson);
        return json.toJSONString();
    }

}
