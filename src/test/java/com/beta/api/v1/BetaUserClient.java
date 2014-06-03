package com.beta.api.v1;

import com.beta.*;
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

    List<BetaUserDTO> getAll() {
        return c.resource(url + "/api/v1/profiles/")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class)
                .getEntity(new GenericType<List<BetaUserDTO>>() {
                });
    }

    Optional<BetaUserDTO> findById(int id) {
        return Optional.fromNullable(c.resource(url + "/api/v1/profiles/" + id)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class)
                .getEntity(BetaUserDTO.class));
    }

    BetaUserDTO add(BetaUserCreation newUser) {
        return c.resource(url + "/api/v1/profiles/add")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, newUser)
                .getEntity(BetaUserDTO.class);
    }

    BetaUserDTO update(int id, BetaUserUpdatesDTO updates) {
        return c.resource(url + "/api/v1/profiles/" + id + "/update")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, updates)
                .getEntity(BetaUserDTO.class);
    }
}
