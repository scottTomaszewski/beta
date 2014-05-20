package com.beta;

import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class ApiV1 implements Api {
    @Override
    public void run(Main.BetaConfig cfg, Environment env) throws ClassNotFoundException {
        final DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        env.jersey().register(new Profiles(dbi));
    }
}
