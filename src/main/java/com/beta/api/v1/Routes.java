package com.beta.api.v1;

import com.beta.Route;
import com.beta.RouteCreationDTO;
import com.beta.RouteDTO;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
    public RouteDTO add(RouteCreationDTO newRoute) {
        return find(dao.insert(newRoute.name, newRoute.grade.getValue()));
    }

    @Timed
    @GET
    @Path("/{id}")
    public RouteDTO find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @Timed
    @GET
    @Path("/")
    public List<RouteDTO> all(@PathParam("id") Integer id) {
        List<RouteDTO> all = new ArrayList<>();
        dao.all().stream().forEach(route -> all.add(route.asDTO()));
        return all;
    }

    @Timed
    @POST
    @Path("/{id}/update")
    public Route update(@PathParam("id") Integer id, Route.OptionalInfo optionals) {
        if (optionals.setterId.isPresent()) dao.updateSetterId(id, optionals.setterId.get());
        if (optionals.tapeColor.isPresent()) dao.updateTapeColor(id, optionals.tapeColor.get());
        return find(id);
    }
}
