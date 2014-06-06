package com.beta.api.v1;

import com.beta.*;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.Arrays;

public class ApiV1 implements Api {
    @Override
    public void run(Main.BetaConfig cfg, Environment env) throws ClassNotFoundException {
        env.jersey().register(com.sun.jersey.multipart.impl.MultiPartReaderServerSide.class);

        final DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        Profiles proRes = new Profiles(dbi.onDemand(ProfilesDAO.class), cfg.getProfilePicturesAbsolutePath());
        Routes routeRes = new Routes(dbi.onDemand(RoutesDAO.class));
        env.jersey().register(proRes);
        env.jersey().register(routeRes);

        try (Handle h = dbi.open()) {
            // setup profiles
            h.execute(BetaUserTable.creation());
            BetaUserCreation[] users = {
                    new BetaUserCreation("cs@gmail.com", "a".toCharArray()),
                    new BetaUserCreation("st@gmail.com", "b".toCharArray()),
                    new BetaUserCreation("rl@gmail.com", "c".toCharArray()),
                    new BetaUserCreation("ba@gmail.com", "d".toCharArray()),
            };
            Arrays.stream(users).forEach(proRes::add);

            // setup routes
            h.execute(RouteTable.creation());
            RouteCreationDTO[] routes = {
                    new RouteCreationDTO("La Dura Dura", RopeGrade._5_15c),
                    new RouteCreationDTO("Fighter", RopeGrade._5_12a),
                    new RouteCreationDTO("Boomerang", BoulderingGrade.V4),
            };
            Arrays.stream(routes).forEach(routeRes::add);
        }
    }
}
