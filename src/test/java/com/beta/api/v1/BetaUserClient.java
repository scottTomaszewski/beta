package com.beta.api.v1;

import com.beta.BetaUser;
import com.beta.Route;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import javax.ws.rs.core.MediaType;
import java.util.List;

public class BetaUserClient {
    private final String url;
    private final Client c;

    BetaUserClient(String baseURL, Client c) {
        this.url = baseURL;
        this.c = c;
    }

    List<BetaUser> getAll() {
        return c.resource(url + "/api/v1/profiles/")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class)
                .getEntity(new GenericType<List<BetaUser>>() {
                });
    }

    Optional<BetaUser> findById(int id) {
        return Optional.fromNullable(c.resource(url + "/api/v1/profiles/" + id)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class)
                .getEntity(BetaUser.class));
    }

    BetaUser add(Route.BaseInfo newRoute) {
        return c.resource(url + "/api/v1/profiles/add")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, newRoute)
                .getEntity(BetaUser.class);
    }

    BetaUser update(int id, Route.OptionalInfo updates) {
        return c.resource(url + "/api/v1/profiles/" + id + "/update")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, updates)
                .getEntity(BetaUser.class);
    }
}
