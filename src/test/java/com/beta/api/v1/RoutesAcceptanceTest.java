package com.beta.api.v1;

import com.beta.Grade;
import com.beta.Main;
import com.beta.RopeGrade;
import com.beta.Route;
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
        c.add(new Route.BaseInfo("Foo Route", RopeGrade._5_10a));
        Assertions.assertThat(beforeCount + 1).isEqualTo(c.getAll().size());
    }

    @Test
    public void newRouteInfoMatches() {
        String name = "Foo Route";
        Grade grade = RopeGrade._5_10a;
        Route added = c.add(new Route.BaseInfo(name, grade));
        Assertions.assertThat(name).isEqualTo(added.info.name);
        Assertions.assertThat(grade).isEqualTo(added.info.grade);
    }

    @Test
    public void updateRouteSetterId() {
        String setterId = "123";
        Route update = c.update(1, new Route.OptionalInfo(Optional.of(setterId), Optional.absent()));
        Assertions.assertThat(setterId).isEqualTo(update.optionals.setterId.get());
    }

    @Test
    public void updateRouteTapeColor() {
        String color = "456";
        Route update = c.update(1, new Route.OptionalInfo(Optional.absent(), Optional.of(color)));
        Assertions.assertThat(color).isEqualTo(update.optionals.tapeColor.get());
    }

    @Test
    public void updateSingleOptionalRouteFieldDoesntWipeOthers() {
        String setterId = "123";
        c.update(1, new Route.OptionalInfo(Optional.of(setterId), Optional.absent()));
        Route update = c.update(1, new Route.OptionalInfo(Optional.absent(), Optional.of("456")));
        Assertions.assertThat(setterId).isEqualTo(update.optionals.setterId.get());
    }
}
