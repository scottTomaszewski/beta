package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteCreationDTO {
    public final String name;
    public final Grade grade;

    public RouteCreationDTO(@JsonProperty("name") String name, @JsonProperty("grade") Grade grade) {
        this.name = name;
        this.grade = grade;
    }
}
