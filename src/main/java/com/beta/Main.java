package com.beta;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSModule;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.skife.jdbi.v2.DBI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class Main extends Application<Main.JModernConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<JModernConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/"));
    }

    @Override
    public void run(JModernConfiguration cfg, Environment env) throws ClassNotFoundException {
        env.jersey().setUrlPattern("/api/*");

        // Manually add JMX reporting (Dropwizard regression)
        JmxReporter.forRegistry(env.metrics()).build().start();

        env.jersey().register(new HelloWorldResource(cfg));

        Feign.Builder feignBuilder = Feign.builder()
                .contract(new JAXRSModule.JAXRSContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder());
        env.jersey().register(new ConsumerResource(feignBuilder));

        final DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        env.jersey().register(new Profiles(dbi));
    }

    // YAML Configuration
    public static class JModernConfiguration extends Configuration {
        @JsonProperty private @NotEmpty String template;
        @JsonProperty private @NotEmpty String defaultName;
        @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory();

        public DataSourceFactory getDataSourceFactory() { return database; }
        public String getTemplate()    { return template; }
        public String getDefaultName() { return defaultName; }
    }

    // The actual service
    @Path("/hello-world")
    @Produces(MediaType.APPLICATION_JSON)
    public static class HelloWorldResource {
        private final AtomicLong counter = new AtomicLong();
        private final String template;
        private final String defaultName;

        public HelloWorldResource(JModernConfiguration configuration) {
            this.template = configuration.getTemplate();
            this.defaultName = configuration.getDefaultName();
        }

        @Timed
        @GET
        public Saying sayHello(@QueryParam("name") Optional<String> name) throws InterruptedException {
            final String value = String.format(template, name.or(defaultName));
            Thread.sleep(ThreadLocalRandom.current().nextInt(10, 500));
            return new Saying(counter.incrementAndGet(), value);
        }
    }

    @Path("/consumer")
    @Produces(MediaType.TEXT_PLAIN)
    public static class ConsumerResource {
        private final HelloWorldAPI helloWorld;

        public ConsumerResource(Feign.Builder feignBuilder) {
            this.helloWorld = feignBuilder.target(HelloWorldAPI.class, "http://localhost:8080");
        }

        @Timed
        @GET
        public String consume() {
            Saying saying = helloWorld.hi("consumer");
            return String.format("The service is saying: %s (id: %d)",  saying.getContent(), saying.getId());
        }
    }

    interface HelloWorldAPI {
        @GET @Path("/hello-world")
        Saying hi(@QueryParam("name") String name);

        @GET @Path("/hello-world")
        Saying hi();
    }

    // JSON (immutable!) payload
    public static class Saying {
        private long id;
        private @Length(max = 10) String content;

        public Saying(long id, String content) {
            this.id = id;
            this.content = content;
        }

        // required for deserialization
        public Saying() {}

        @JsonProperty public long getId() { return id; }
        @JsonProperty public String getContent() { return content; }
    }
}
