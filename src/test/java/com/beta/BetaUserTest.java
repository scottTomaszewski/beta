package com.beta;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.fest.assertions.api.Assertions.assertThat;

import com.google.common.base.Optional;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.CharBuffer;

public class BetaUserTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        BetaUser.OptionalInfo i = new BetaUser
                .OptionalInfo(Optional.absent(), Optional.absent(), Optional.absent());
        BetaUser u = new BetaUser(1, "cs@gmail.com", "unused", "unused", i);
        assertThat(MAPPER.writeValueAsString(u)).isEqualTo(fixture("fixtures/NewBetaUser.json"));
    }
}
