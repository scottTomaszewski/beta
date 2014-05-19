package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BetaUser {
    @JsonProperty
    public final int id;
    @JsonProperty public final String name;

    public BetaUser(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
