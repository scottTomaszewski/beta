package com.beta.api.v1;

import com.beta.BetaUser;
import com.beta.Main;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.fest.assertions.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


public class NewBetaUserAcceptanceTest {
    @ClassRule
    public static final DropwizardAppRule<Main.BetaConfig> RULE =
            new DropwizardAppRule<>(Main.class, Resources.getResource("beta.yml").getPath());

    @Test
    public void loginHandlerRedirectsAfterPost() throws IOException {
        Client client = new Client();

        int count = client.resource(
                String.format("http://localhost:%d/api/v1/profiles/", RULE.getLocalPort()))
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class).getEntity(new GenericType<List<BetaUser>>(){}).size();
        ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/v1/profiles/add", RULE.getLocalPort()))
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, "{\"email\":\"foo@bar.com\",\"plainTextPassword\":\"foo\"}");
        int afterCount  = client.resource(
                String.format("http://localhost:%d/api/v1/profiles/", RULE.getLocalPort()))
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class).getEntity(new GenericType<List<BetaUser>>(){}).size();
        Assertions.assertThat(count+1).isEqualTo(afterCount);
    }
}
