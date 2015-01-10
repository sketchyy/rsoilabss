package com.sketchyy.rsoi3.movies;

import com.sketchyy.rsoi3.movies.resources.MovieResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sketchy on 01.01.2015.
 */
public class MoviesApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MovieResource.class);
        return classes;
    }
}
