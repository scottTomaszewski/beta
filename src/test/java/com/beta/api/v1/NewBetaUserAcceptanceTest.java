package com.beta.api.v1;

import com.beta.Main;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static io.dropwizard.testing.FixtureHelpers.fixture;


public class NewBetaUserAcceptanceTest {
    @ClassRule
    public static final DropwizardAppRule<Main.BetaConfig> RULE =
            new DropwizardAppRule<>(Main.class, "beta.yaml");

    @Test
    public void loginHandlerRedirectsAfterPost() throws IOException {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/api/v1/profiles/add", RULE.getLocalPort()))
                .post(ClientResponse.class, fixture("fixtures/NewBetaUser.json"));

        System.out.println(CharStreams.toString(new InputStreamReader(response.getEntityInputStream(), Charsets.UTF_8)));
    }
}
