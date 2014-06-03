package com.beta;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.fest.assertions.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.CharBuffer;

public class BetaUserTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        BetaUser u = new BetaUser(1, "cs@gmail.com", Optional.absent(), Optional.absent(),
                Optional.absent(), "unused", "unused");
        assertThat(MAPPER.writeValueAsString(u)).isEqualTo(fixture("fixtures/NewBetaUser.json"));
    }
}
