package com.sketchyy.rsoi3.actors.entities;

import javax.persistence.*;

/**
 * Created by Sketchy on 10.01.2015.
 */
@Entity
@Table(name = "RSOI2_ACTORS_MOVIES", schema = "SKETCHYY", catalog = "")
@IdClass(ActorsMoviesPK.class)
public class ActorsMovies {
    private int actorId;
    private int movieId;

    @Id
    @Column(name = "ACTOR_ID")
    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Id
    @Column(name = "MOVIE_ID")
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActorsMovies that = (ActorsMovies) o;

        if (actorId != that.actorId) return false;
        if (movieId != that.movieId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actorId;
        result = 31 * result + movieId;
        return result;
    }
}
