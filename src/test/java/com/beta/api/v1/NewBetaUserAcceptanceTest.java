package com.beta.api.v1;

import com.beta.*;
import com.google.common.base.Optional;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.fest.assertions.api.Assertions;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class NewBetaUserAcceptanceTest {
    @ClassRule
    public static final DropwizardAppRule<Main.BetaConfig> RULE =
            new DropwizardAppRule<>(Main.class, Resources.getResource("beta.yml").getPath());

    private static BetaUserClient c;

    @BeforeClass
    public static void setup() {
        Client jc = new JerseyClientBuilder(RULE.getEnvironment()).build("testClient");
        c = new BetaUserClient(String.format("http://localhost:%d", RULE.getLocalPort()), jc);
    }

    @Test
    public void newBetaUserIncrementsUserCount() {
        int beforeCount = c.getAll().size();
        c.add(new BetaUserCreation("Foo@bar.com", "pass".toCharArray()));
        Assertions.assertThat(beforeCount + 1).isEqualTo(c.getAll().size());
    }

    @Test
    public void newBetaUserInfoMatches() {
        String email = "foo@bar.com";
        String plaintextImmutable = "plain";
        BetaUserDTO added = c.add(new BetaUserCreation(email, plaintextImmutable.toCharArray()));
        Assertions.assertThat(email).isEqualTo(added.email);
    }

    @Test
    public void updateBetaUserEmail() {
        String email = "foo@bar.com";
        BetaUserDTO update = c.update(1, new BetaUserUpdatesDTO(
                Optional.of(email), Optional.absent(), Optional.absent()));
        Assertions.assertThat(email).isEqualTo(update.email);
    }

    @Test
    public void updateBetaUserFirstName() {
        String first = "Chris";
        BetaUserDTO update = c.update(1, new BetaUserUpdatesDTO(
                Optional.absent(), Optional.of(first), Optional.absent()));
        Assertions.assertThat(first).isEqualTo(update.firstName.get());
    }

    @Test
    public void updateBetaUserLastName() {
        String last = "Sharma";
        BetaUserDTO update = c.update(1, new BetaUserUpdatesDTO(
                Optional.absent(), Optional.absent(), Optional.of(last)));
        Assertions.assertThat(last).isEqualTo(update.lastName.get());
    }

    @Test
    public void updateSingleOptionalBetaUserFieldDoesntWipeOthers() {
        String first = "Chris";
        c.update(1, new BetaUserUpdatesDTO(
                Optional.absent(), Optional.of(first), Optional.absent()));
        BetaUserDTO update = c.update(1, new BetaUserUpdatesDTO(
                Optional.absent(), Optional.absent(), Optional.of("Sharma")));
        Assertions.assertThat(first).isEqualTo(update.firstName.get());
    }
}
