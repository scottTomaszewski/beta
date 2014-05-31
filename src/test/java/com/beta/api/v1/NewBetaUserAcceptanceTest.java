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
        BetaUser added = c.add(new BetaUserCreation(email, plaintextImmutable.toCharArray()));
        Assertions.assertThat(email).isEqualTo(added.email);
        // TODO
        //Assertions.assertThat(added.checkPassword(plaintextImmutable.toCharArray())).isTrue();
    }

    @Test
    public void updateBetaUserFirstName() {
        String first = "Chris";
        BetaUser update = c.update(1, new BetaUser.OptionalInfo(
                Optional.of(first), Optional.absent(), Optional.absent()));
        Assertions.assertThat(first).isEqualTo(update.optionals.firstName.get());
    }

    @Test
    public void updateBetaUserLastName() {
        String last = "Sharma";
        BetaUser update = c.update(1, new BetaUser.OptionalInfo(
                Optional.absent(), Optional.of(last), Optional.absent()));
        Assertions.assertThat(last).isEqualTo(update.optionals.lastName.get());
    }

    @Test
    public void updateSingleOptionalBetaUserFieldDoesntWipeOthers() {
        String first = "Chris";
        c.update(1, new BetaUser.OptionalInfo(
                Optional.of(first), Optional.absent(), Optional.absent()));
        BetaUser update = c.update(1, new BetaUser.OptionalInfo(
                Optional.absent(), Optional.of("Sharma"), Optional.absent()));
        Assertions.assertThat(first).isEqualTo(update.optionals.firstName.get());
    }
}
