package com.beta;
import com.codahale.metrics.*;
import com.codahale.metrics.annotation.*;
import com.fasterxml.jackson.annotation.*;
import com.google.common.base.Optional;
import feign.Feign;
import feign.jackson.*;
import feign.jaxrs.*;
import io.dropwizard.Application;
import io.dropwizard.*;
import io.dropwizard.db.*;
import io.dropwizard.jdbi.*;
import io.dropwizard.setup.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.hibernate.validator.constraints.*;
import org.skife.jdbi.v2.*;
import org.skife.jdbi.v2.util.*;

public class Main extends Application<Main.JModernConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<JModernConfiguration> bootstrap) {
    }

    @Override
    public void run(JModernConfiguration cfg, Environment env) throws ClassNotFoundException {
        // Manually add JMX reporting (Dropwizard regression)
        JmxReporter.forRegistry(env.metrics()).build().start();

        env.jersey().register(new HelloWorldResource(cfg));

        Feign.Builder feignBuilder = Feign.builder()
                .contract(new JAXRSModule.JAXRSContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder());
        env.jersey().register(new ConsumerResource(feignBuilder));

        final DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        env.jersey().register(new DBResource(dbi));
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

        @Timed // monitor timing of this service with Metrics
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
        private final HelloWorldAPI hellowWorld;

        public ConsumerResource(Feign.Builder feignBuilder) {
            this.hellowWorld = feignBuilder.target(HelloWorldAPI.class, "http://localhost:8080");
        }

        @Timed
        @GET
        public String consume() {
            Saying saying = hellowWorld.hi("consumer");
            return String.format("The service is saying: %s (id: %d)",  saying.getContent(), saying.getId());
        }
    }

    @Path("/db")
    @Produces(MediaType.APPLICATION_JSON)
    public static class DBResource {
        private final DBI dbi;

        public DBResource(DBI dbi) {
            this.dbi = dbi;

            try (Handle h = dbi.open()) {
                h.execute("create table something (id int primary key auto_increment, name varchar(100))");
                String[] names = { "Gigantic", "Bone Machine", "Hey", "Cactus" };
                Arrays.stream(names).forEach(name -> h.insert("insert into something (name) values (?)", name));
            }
        }

        @Timed
        @POST @Path("/add")
        public Map<String, Object> add(String name) {
            try (Handle h = dbi.open()) {
                int id = h.createStatement("insert into something (name) values (:name)").bind("name", name)
                        .executeAndReturnGeneratedKeys(IntegerMapper.FIRST).first();
                return find(id);
            }
        }

        @Timed
        @GET @Path("/item/{id}")
        public Map<String, Object> find(@PathParam("id") Integer id) {
            try (Handle h = dbi.open()) {
                return h.createQuery("select id, name from something where id = :id").bind("id", id).first();
            }
        }

        @Timed
        @GET @Path("/all")
        public List<Map<String, Object>> all(@PathParam("id") Integer id) {
            try (Handle h = dbi.open()) {
                return h.createQuery("select * from something").list();
            }
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

        public Saying() {} // required for deserialization

        @JsonProperty public long getId() { return id; }
        @JsonProperty public String getContent() { return content; }
    }
}