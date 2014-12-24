package com.beta.api.v1;

import com.beta.BetaPreviewEmailDTO;
import com.beta.BetaUserCreation;
import com.beta.BetaUserDTO;
import com.beta.UserPreviewEmail;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;

@Path("/v1/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class Emails {
    private final EmailDAO dao;

    public Emails(EmailDAO emailsDao) {
        this.dao = emailsDao;
    }

    @Timed
    @POST
    @Path("/add")
    public BetaPreviewEmailDTO add(UserPreviewEmail email) {
        return find(dao.insert(email.getEmail(), LocalDate.now()));
    }

    @Timed
    @GET
    @Path("/{id}")
    public BetaPreviewEmailDTO find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }
}
