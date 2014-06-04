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

public class RoutesAcceptanceTest {
    @ClassRule
    public static final DropwizardAppRule<Main.BetaConfig> RULE =
            new DropwizardAppRule<>(Main.class, Resources.getResource("beta.yml").getPath());

    private static RoutesClient c;

    @BeforeClass
    public static void setup() {
        Client jc = new JerseyClientBuilder(RULE.getEnvironment()).build("testClient");
        c = new RoutesClient(String.format("http://localhost:%d", RULE.getLocalPort()), jc);
    }

    @Test
    public void newRouteIncrementsRouteCount() {
        int beforeCount = c.getAll().size();
        c.add(new RouteCreationDTO("Foo Route", RopeGrade._5_10a));
        Assertions.assertThat(beforeCount + 1).isEqualTo(c.getAll().size());
    }

    @Test
    public void newRouteInfoMatches() {
        String name = "Foo Route";
        Grade grade = RopeGrade._5_10a;
        RouteDTO added = c.add(new RouteCreationDTO(name, grade));
        Assertions.assertThat(name).isEqualTo(added.name);
        Assertions.assertThat(grade).isEqualTo(added.grade);
    }

    @Test
    public void updateRouteSetterId() {
        String setterId = "123";
        RouteDTO update = c.update(1, new RouteUpdatesDTO(
                Optional.absent(), Optional.absent(), Optional.of(setterId), Optional.absent()));
        Assertions.assertThat(setterId).isEqualTo(update.setterId.get());
    }

    @Test
    public void updateRouteTapeColor() {
        String color = "456";
        RouteDTO update = c.update(1, new RouteUpdatesDTO(
                Optional.absent(), Optional.absent(), Optional.absent(), Optional.of(color)));
        Assertions.assertThat(color).isEqualTo(update.tapeColor.get());
    }

    @Test
    public void updateSingleOptionalRouteFieldDoesntWipeOthers() {
        String setterId = "123";
        c.update(1, new RouteUpdatesDTO(
                Optional.absent(), Optional.absent(), Optional.of(setterId), Optional.absent()));
        RouteDTO update = c.update(1, new RouteUpdatesDTO(
                Optional.absent(), Optional.absent(), Optional.absent(), Optional.of("456")));
        Assertions.assertThat(setterId).isEqualTo(update.setterId.get());
    }
}
