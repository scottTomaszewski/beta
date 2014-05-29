package com.beta.api.v1;

import com.beta.Main;
import com.beta.RopeGrade;
import com.beta.Route;
import com.google.common.io.Resources;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

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
    public void getAll() {
        List<Route> all = c.getAll();
        for(Route r : all) {
            System.out.println(r);
        }
        System.out.println(c.add(new Route.BaseInfo("foo", RopeGrade._5_0)));
    }
}
