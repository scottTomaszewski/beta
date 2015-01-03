package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BetaPreviewEmailDTO {
    public final int id;
    public final String email;
    private final LocalDate date;

    BetaPreviewEmailDTO(@JsonProperty("id") int id,
                @JsonProperty("email") String email,
                @JsonProperty("date") String date) {
        this.id = id;
        this.email = email;
        this.date = LocalDate.parse(date);
    }
}
