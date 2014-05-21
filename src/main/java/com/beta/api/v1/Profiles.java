package com.beta.api.v1;

import com.beta.BetaUser;
import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/v1/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class Profiles {
    private final ProfilesDAO dao;

    public Profiles(DBI dbi) {
        this.dao = dbi.onDemand(ProfilesDAO.class);

        try (Handle h = dbi.open()) {
            h.execute("create table beta_user (" +
                    "id int primary key auto_increment" +
                    ", email varchar(100)" +
                    ", passwordHash varchar(100)" +
                    ", firstName varchar(100)" +
                    ", lastName varchar(100)" +
                    ", profilePictureRelativePath varchar(100)" +
                    ")");
            BetaUser.BaseInfo[] users = {
                    new BetaUser.BaseInfo("hp@gmail.com", "a"),
                    new BetaUser.BaseInfo("st@gmail.com", "b"),
                    new BetaUser.BaseInfo("rl@gmail.com", "c"),
                    new BetaUser.BaseInfo("ba@gmail.com", "d"),
            };
            Arrays.stream(users).forEach(user -> this.add(user));
        }
    }

    @Timed
    @POST
    @Path("/add")
    public BetaUser add(BetaUser.BaseInfo newGuy) {
        return find(dao.insert(newGuy.email(), newGuy.passwordHash()));
    }

    @Timed
    @GET
    @Path("/{id}")
    public BetaUser find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @Timed
    @GET
    @Path("/")
    public List<BetaUser> all(@PathParam("id") Integer id) {
        return dao.all();
    }

    @Timed
    @POST
    @Path("/{id}/update")
    public void update(@PathParam("id") Integer id, BetaUser.OptionalInfo optionals) {
        if (!optionals.getFirstName().isEmpty()) dao.updateFirstName(id, optionals.getFirstName());
        if (!optionals.getFirstName().isEmpty()) dao.updateLastName(id, optionals.getLastName());
    }
}
