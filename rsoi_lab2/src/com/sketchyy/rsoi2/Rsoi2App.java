package com.sketchyy.rsoi2;

import com.sketchyy.rsoi2.resources.ActorResource;
import com.sketchyy.rsoi2.resources.MovieResource;
import com.sketchyy.rsoi2.resources.OAuthResource;
import com.sketchyy.rsoi2.resources.InfoResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sketchy on 13.12.2014.
 */
public class Rsoi2App extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MovieResource.class);
        classes.add(ActorResource.class);
        classes.add(InfoResource.class);
        classes.add(OAuthResource.class);
        return classes;
    }
}
