package com.sketchyy.rsoi3.session;

import com.sketchyy.rsoi3.session.resources.SessionResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sketchy on 09.01.2015.
 */
public class SessionApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(SessionResource.class);
        return classes;
    }
}
