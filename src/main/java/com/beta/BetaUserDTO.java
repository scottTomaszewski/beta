package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;

public class BetaUserDTO {
    public final int id;
    public final String email;
    public final Optional<String> firstName;
    public final Optional<String> lastName;
    public final Optional<String> pictureAbsolutePath;

    @VisibleForTesting
    BetaUserDTO(@JsonProperty("id") int id,
                @JsonProperty("email") String email,
                @JsonProperty("firstName") Optional<String> firstName,
                @JsonProperty("lastName") Optional<String> lastName,
                @JsonProperty("pictureAbsolutePath") Optional<String> pictureAbsolutePath) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureAbsolutePath = pictureAbsolutePath;
    }
}
