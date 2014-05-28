package com.beta.api.v1;

import com.beta.Route;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import javax.ws.rs.core.MediaType;
import java.util.List;

public class RoutesClient {
    private final String url;
    private final Client c;

    public RoutesClient(String baseURL) {
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
}
