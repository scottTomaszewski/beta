package com.beta.api.v1;

import com.beta.RouteCreationDTO;
import com.beta.RouteDTO;
import com.beta.RouteUpdatesDTO;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import javax.ws.rs.core.MediaType;
import java.util.List;

class RoutesClient {
    private final String url;
    private final Client c;

    RoutesClient(String baseURL, Client c) {
        this.url = baseURL;
        this.c = c;
    }

    List<RouteDTO> getAll() {
        return c.resource(url + "/api/v1/routes/")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class)
                .getEntity(new GenericType<List<RouteDTO>>() {
                });
    }

    Optional<RouteDTO> findById(int id) {
        return Optional.fromNullable(c.resource(url + "/api/v1/routes/" + id)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class)
                .getEntity(RouteDTO.class));
    }

    RouteDTO add(RouteCreationDTO newRoute) {
        return c.resource(url + "/api/v1/routes/add")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, newRoute)
                .getEntity(RouteDTO.class);
    }

    RouteDTO update(int id, RouteUpdatesDTO updates) {
        return c.resource(url + "/api/v1/routes/" + id + "/update")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, updates)
                .getEntity(RouteDTO.class);
    }
}