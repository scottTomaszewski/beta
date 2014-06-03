package com.beta.api.v1;

import com.beta.BetaUser;
import com.beta.BetaUserCreation;
import com.beta.BetaUserUpdatesDTO;
import com.codahale.metrics.annotation.Timed;
import com.google.common.io.Files;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Path("/v1/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class Profiles {
    private final ProfilesDAO dao;
    private final String profilePicturesAbsolutePath;

    public Profiles(ProfilesDAO registered, String profilePicturesAbsolutePath) {
        this.dao = registered;
        this.profilePicturesAbsolutePath = profilePicturesAbsolutePath;
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
    public BetaUser update(@PathParam("id") Integer id, BetaUserUpdatesDTO data) {
        // TODO - support email updates
        if (data.firstName.isPresent()) dao.updateFirstName(id, data.firstName.get());
        if (data.lastName.isPresent()) dao.updateLastName(id, data.lastName.get());
        return find(id);
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
        dao.updatePictureLocation(id, file.getPath());
        return file.getPath();
    }
}
