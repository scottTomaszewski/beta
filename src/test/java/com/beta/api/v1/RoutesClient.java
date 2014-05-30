package com.beta.api.v1;

import com.beta.Route;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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

    Route add(Route.BaseInfo newRoute) {
        return c.resource(url + "/api/v1/routes/add")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, newRoute).getEntity(Route.class);
    }

    Route update(int id, Route.OptionalInfo updates) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(updates);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("NARGLES: " + jsonString);
        return c.resource(url + "/api/v1/routes/" + id + "/update")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, updates).getEntity(Route.class);
    }
}