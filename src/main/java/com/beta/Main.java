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
import io.dropwizard.setup.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.hibernate.validator.constraints.*;
import org.jooq.Batch;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import static org.jooq.impl.DSL.*;
import org.jooq.impl.DefaultRecordMapper;


public class Main extends Application<Main.JModernConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<JModernConfiguration> bootstrap) {
    }

    @Override
    public void run(JModernConfiguration cfg, Environment env) throws Exception {
        JmxReporter.forRegistry(env.metrics()).build().start(); // Manually add JMX reporting (Dropwizard regression)

        env.jersey().register(new HelloWorldResource(cfg));

        Feign.Builder feignBuilder = Feign.builder()
                .contract(new JAXRSModule.JAXRSContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder());
        env.jersey().register(new ConsumerResource(feignBuilder));

        DataSource ds = cfg.getDataSourceFactory().build(env.metrics(), "db"); // Dropwizard will monitor the connection pool
        env.jersey().register(new DBResource(ds));
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
        private final DataSource ds;
        private static final RecordMapper<Record, Something> toSomething =
                record -> new Something(record.getValue(field("id", Integer.class)), record.getValue(field("name", String.class)));

        public DBResource(DataSource ds) throws SQLException {
            this.ds = ds;

            try (Connection conn = ds.getConnection()) {
                conn.createStatement().execute("create table something (id int primary key auto_increment, name varchar(100))");

                String[] names = { "Gigantic", "Bone Machine", "Hey", "Cactus" };
                DSLContext context = using(conn);
                Arrays.stream(names).forEach(name -> context.insertInto(table("something"), field("name")).values(name).execute());
            }
        }

        @Timed
        @POST @Path("/add")
        public Something add(String name) throws SQLException {
            try (Connection conn = ds.getConnection()) {
                // this does not work
                int id = using(conn).insertInto(table("something"), field("name")).values(name).returning(field("id")).fetchOne().into(Integer.class);
                return find(id);
            }
        }

        @Timed
        @GET @Path("/item/{id}")
        public Something find(@PathParam("id") Integer id) throws SQLException {
            try (Connection conn = ds.getConnection()) {
                return using(conn).select(field("id"), field("name")).from(table("something"))
                        .where(field("id", Integer.class).equal(id)).fetchOne().map(toSomething);
            }
        }

        @Timed
        @GET @Path("/all")
        public List<Something> all(@PathParam("id") Integer id) throws SQLException {
            try (Connection conn = ds.getConnection()) {
                return using(conn).select(field("id"), field("name")).from(table("something")).fetch().map(toSomething);
            }
        }
    }

    public static class Something {
        @JsonProperty public final int id;
        @JsonProperty public final String name;

        public Something(int id, String name) {
            this.id = id;
            this.name = name;
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