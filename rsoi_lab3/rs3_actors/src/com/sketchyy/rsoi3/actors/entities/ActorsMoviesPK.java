package com.sketchyy.rsoi3.actors.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sketchy on 10.01.2015.
 */
public class ActorsMoviesPK implements Serializable {
    private int actorId;
    private int movieId;

    @Column(name = "ACTOR_ID")
    @Id
    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Column(name = "MOVIE_ID")
    @Id
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

        ActorsMoviesPK that = (ActorsMoviesPK) o;

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
