package com.beta.api.v1;

import com.beta.BetaUser;
import com.codahale.metrics.annotation.Timed;
import com.google.common.io.Files;
import com.sun.jersey.multipart.FormDataParam;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Path("/v1/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class Profiles {
    //TODO - make configurable
    private static final String UPLOAD_DIR = Files.createTempDir().getAbsolutePath() + "/profilePictures";

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
                    ", profilePictureAbsolutePath varchar(1024)" +
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
        if (!optionals.getLastName().isEmpty()) dao.updateLastName(id, optionals.getLastName());
    }

    @POST
    @Path("/{id}/updateProfilePicture")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@PathParam("id") Integer id,
                             @FormDataParam("file") final InputStream picture) throws IOException {
        File file = new File(UPLOAD_DIR + File.separator + id + File.separator + UUID.randomUUID());
        new File(file.getParent()).mkdirs();
        Files.asByteSink(file).writeFrom(picture);
        dao.updateProfilePictureLocation(id, file.getPath());
        return file.getPath();
    }
}
