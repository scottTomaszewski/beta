package com.beta.api.v1;

import com.beta.Grade;
import com.beta.Main;
import com.beta.RopeGrade;
import com.beta.Route;
import com.google.common.io.Resources;
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
        c = new RoutesClient(String.format("http://localhost:%d", RULE.getLocalPort()));
    }

    @Test
    public void newRouteIncrementsUserCount() {
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
}
