package com.beta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

public class RouteUpdatesDTO {
    public final Optional<String> name;
    public final Optional<Grade> grade;
    public final Optional<String> setterId;
    public final Optional<String> tapeColor;

    @JsonCreator
    public RouteUpdatesDTO(
            @JsonProperty("name") Optional<String> name,
            @JsonProperty("grade") Optional<Grade> grade,
            @JsonProperty("setterId") Optional<String> setterId,
            @JsonProperty("tapeColor") Optional<String> tapeColor) {
        this.name = name;
        this.grade = grade;
        this.setterId = setterId;
        this.tapeColor = tapeColor;
    }
}
