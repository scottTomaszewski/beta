package com.beta.api.v1;

import com.beta.Main;
import com.google.common.io.Resources;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
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
    public void getAll() {
        System.out.println(c.getAll());
    }
}
