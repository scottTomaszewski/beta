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
        final RouteDTO r = new RouteDTO(1, "route", RopeGrade._5_1, Optional.absent(), Optional.absent());
        assertThat(MAPPER.writeValueAsString(r)).isEqualTo(fixture("fixtures/Route.json"));
    }
}
