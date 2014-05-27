package com.beta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.fest.assertions.api.Assertions.assertThat;

public class RouteTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        Route.BaseInfo b = new Route.BaseInfo("route", RopeGrade._5_1);
        Route.OptionalInfo o = new Route.OptionalInfo(Optional.absent(), Optional.absent());
        final Route Route = new Route(1, b, o);
        assertThat(MAPPER.writeValueAsString(Route))
                .isEqualTo(fixture("fixtures/Route.json"));
    }
}
