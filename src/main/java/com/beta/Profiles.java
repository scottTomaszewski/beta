package com.beta;

import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class Profiles {
    private final ProfilesDAO dao;

    public Profiles(DBI dbi) {
        this.dao = dbi.onDemand(ProfilesDAO.class);

        try (Handle h = dbi.open()) {
            h.execute("create table beta_user (" +
                    "id int primary key auto_increment" +
                    ", firstName varchar(100)" +
                    ", lastName varchar(100)" +
                    ", email varchar(100)" +
                    ", passwordHash varchar(100)" +
                    ", profilePictureRelativeUrl varchar(100)" +
                    ")");
            BetaUser.BaseInfo[] users = {
                    new BetaUser.BaseInfo("Harris", "Phau", "hp@gmail.com", "a"),
                    new BetaUser.BaseInfo("Scott", "Tomaszewski", "st@gmail.com", "b"),
                    new BetaUser.BaseInfo("Ryan", "Longchamps", "rl@gmail.com", "c"),
                    new BetaUser.BaseInfo("Bryan", "Absher", "ba@gmail.com", "d"),
            };
            Arrays.stream(users).forEach(user -> this.add(user));
        }
    }

    @Timed
    @POST
    @Path("/add")
        public BetaUser add(BetaUser.BaseInfo newGuy) {
        return find(dao.insert(newGuy.firstName(),
                newGuy.lastName(),
                newGuy.email(),
                newGuy.passwordHash()));
    }

    @Timed
    @GET
    @Path("/{id}")
    public BetaUser find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @Timed
    @GET @Path("/")
    public List<BetaUser> all(@PathParam("id") Integer id) {
        return dao.all();
    }
}
