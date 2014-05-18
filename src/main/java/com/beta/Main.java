package com.beta;

import io.dropwizard.Application;
import io.dropwizard.*;
import io.dropwizard.setup.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

public class Main extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server"});
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        environment.jersey().register(new HelloWorldResource());
    }

    @Path("/hello-world")
    @Produces(MediaType.APPLICATION_JSON)
    public static class HelloWorldResource {
        private final AtomicLong counter = new AtomicLong();

        @GET
        public Map<String, Object> sayHello(@QueryParam("name") String name) {
            Map<String, Object> res = new HashMap<>();
            res.put("id", counter.incrementAndGet());
            res.put("content", "Hello, " + (name != null ? name : "World"));
            return res;
        }
    }
}