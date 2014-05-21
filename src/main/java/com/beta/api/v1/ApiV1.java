package com.beta.api.v1;

import com.beta.Api;
import com.beta.Main;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class ApiV1 implements Api {
    @Override
    public void run(Main.BetaConfig cfg, Environment env) throws ClassNotFoundException {
        env.jersey().register(com.sun.jersey.multipart.impl.MultiPartReaderServerSide.class);
        final DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        env.jersey().register(new Profiles(dbi));
    }
}
