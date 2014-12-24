package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPreviewEmail {
    private final String email;

    public UserPreviewEmail(@JsonProperty("email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
