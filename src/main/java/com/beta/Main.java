package com.beta;

import com.beta.api.v1.ApiV1;
import com.codahale.metrics.JmxReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Main extends Application<Main.BetaConfig> {
    public static void main(String[] args) throws Exception {
        new Main().run(new String[]{"server", System.getProperty("dropwizard.config")});
    }

    @Override
    public void initialize(Bootstrap<BetaConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/app", "/", "index.html", "app"));
        bootstrap.addBundle(new AssetsBundle("/bower_components", "/lib", "index.html", "js"));
    }

    @Override
    public void run(BetaConfig cfg, Environment env) throws ClassNotFoundException {
        env.jersey().setUrlPattern("/api/*");

        // Manually add JMX reporting (Dropwizard regression)
        JmxReporter.forRegistry(env.metrics()).build().start();

        new ApiV1().run(cfg, env);
    }

    // YAML Configuration
    public static class BetaConfig extends Configuration {
        @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory();

        public DataSourceFactory getDataSourceFactory() { return database; }
    }
}
