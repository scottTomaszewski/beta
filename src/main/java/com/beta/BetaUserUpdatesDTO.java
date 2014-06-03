package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

public class BetaUserUpdatesDTO {
    public final Optional<String> email;
    public final Optional<String> firstName;
    public final Optional<String> lastName;

    BetaUserUpdatesDTO(@JsonProperty("email") Optional<String> email,
                       @JsonProperty("firstName") Optional<String> firstName,
                       @JsonProperty("lastName") Optional<String> lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
