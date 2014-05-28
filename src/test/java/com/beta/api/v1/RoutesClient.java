package com.beta.api.v1;

import com.beta.Route;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import javax.ws.rs.core.MediaType;
import java.util.List;

class RoutesClient {
    private final String url;
    private final Client c;

    RoutesClient(String baseURL) {
        this.url = baseURL;
        c = new Client();
    }

    List<Route> getAll() {
        return c.resource(url + "/api/v1/routes/")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class).getEntity(new GenericType<List<Route>>() {
                });

    }

    Optional<Route> findById(int id) {
        return Optional.fromNullable(c.resource(url + "/api/v1/routes/" + id)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class).getEntity(Route.class));
    }
}