package com.beta.api.v1;

import com.beta.BetaUser;
import com.beta.BetaUserCreation;
import com.beta.BetaUserTable;
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
    private final ProfilesDAO dao;
    private final String profilePicturesAbsolutePath;

    public Profiles(DBI dbi, String profilePicturesAbsolutePath) {
        this.dao = dbi.onDemand(ProfilesDAO.class);
        this.profilePicturesAbsolutePath = profilePicturesAbsolutePath;

        try (Handle h = dbi.open()) {
            h.execute(BetaUserTable.creation());
            BetaUserCreation[] users = {
                    new BetaUserCreation("cs@gmail.com", "a".toCharArray()),
                    new BetaUserCreation("st@gmail.com", "b".toCharArray()),
                    new BetaUserCreation("rl@gmail.com", "c".toCharArray()),
                    new BetaUserCreation("ba@gmail.com", "d".toCharArray()),
            };
            Arrays.stream(users).forEach(this::add);
        }
    }

    @Timed
    @POST
    @Path("/add")
    public BetaUser add(BetaUserCreation user) {
        return find(dao.insert(user.email(), user.hashAndSaltPasswordThenClear(), user.salt()));
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
        File file = new File(profilePicturesAbsolutePath + File.separator
                + id + File.separator + UUID.randomUUID());
        if (!new File(file.getParent()).mkdirs()) {
            throw new IOException("Can't save picture because root directory can't be created");
        }
        Files.asByteSink(file).writeFrom(picture);
        dao.updateProfilePictureLocation(id, file.getPath());
        return file.getPath();
    }
}
