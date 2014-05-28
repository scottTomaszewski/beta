package com.beta.api.v1;

import com.beta.Route;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/routes")
@Produces(MediaType.APPLICATION_JSON)
public class Routes {
    private final RoutesDAO dao;

    public Routes(RoutesDAO registered) {
        this.dao = registered;
    }

    @Timed
    @POST
    @Path("/add")
    public Route add(Route.BaseInfo newRoute) {
        return find(dao.insert(newRoute.name, newRoute.grade.getValue()));
    }

    @Timed
    @GET
    @Path("/{id}")
    public Route find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @Timed
    @GET
    @Path("/")
    public List<Route> all(@PathParam("id") Integer id) {
        return dao.all();
    }

    @Timed
    @POST
    @Path("/{id}/update")
    public Route update(@PathParam("id") Integer id, Route.OptionalInfo optionals) {
        if (optionals.getSetterId().isPresent()) dao.updateSetterId(id, optionals.getSetterId().get());
        if (optionals.getTapeColor().isPresent()) dao.updateTapeColor(id, optionals.getTapeColor().get());
        return find(id);
    }
}
