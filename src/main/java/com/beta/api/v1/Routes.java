package com.beta.api.v1;

import com.beta.*;
import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/v1/routes")
@Produces(MediaType.APPLICATION_JSON)
public class Routes {
    private final RoutesDAO dao;

    public Routes(DBI dbi) {
        this.dao = dbi.onDemand(RoutesDAO.class);

        try (Handle h = dbi.open()) {
            h.execute(RouteTable.creation());
            Route.BaseInfo[] routes = {
                    new Route.BaseInfo("La Dura Dura", RopeGrade._5_15c),
                    new Route.BaseInfo("Fighter", RopeGrade._5_12a),
                    new Route.BaseInfo("Boomerang", BoulderingGrade.V4),
            };
            Arrays.stream(routes).forEach(this::add);
        }
    }

    @Timed
    @POST
    @Path("/add")
    public Route add(Route.BaseInfo newRoute) {
        return find(dao.insert(newRoute.name(), newRoute.grade().getValue()));
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
