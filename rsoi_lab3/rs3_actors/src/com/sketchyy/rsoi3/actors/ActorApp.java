package com.sketchyy.rsoi3.actors;

import com.sketchyy.rsoi3.actors.Resource.ActorResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sketchy on 01.01.2015.
 */
public class ActorApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ActorResource.class);
        return classes;
    }
}
