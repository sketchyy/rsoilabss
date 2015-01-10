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
@Table(name = "RSOI2_ACTORS", schema = "SKETCHYY", catalog = "")
@NamedQuery(name = "AllActors", query = "select a from Actor a")
@XmlRootElement
public class Actor {
    private int actorId;
    private String name;
    private Integer height;
    private String country;
    private String gender;
    private List<Movie> movies;

    @Id
    @Column(name = "ACTOR_ID")
    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "HEIGHT")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Basic
    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "GENDER")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (actorId != actor.actorId) return false;
        if (country != null ? !country.equals(actor.country) : actor.country != null) return false;
        if (gender != null ? !gender.equals(actor.gender) : actor.gender != null) return false;
        if (height != null ? !height.equals(actor.height) : actor.height != null) return false;
        if (name != null ? !name.equals(actor.name) : actor.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actorId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "actors")
    @XmlTransient
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String buildJSONString() {
        JSONObject json = new JSONObject();
        json.put("actorId", actorId);
        json.put("name", name);
        json.put("height", height);
        json.put("country", country);
        json.put("gender", gender);
        JSONArray moviesJson = new JSONArray();

        for (Movie m : movies) {
            JSONObject movieJson = new JSONObject();
            movieJson.put("movieId", m.getMovieId());
            movieJson.put("title", m.getTitle());
            movieJson.put("genre", m.getGenre());
            movieJson.put("year", m.getYear());
            moviesJson.add(movieJson);
        }

        json.put("movies", moviesJson);
        return json.toJSONString();
    }
}
