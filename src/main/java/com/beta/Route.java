package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

import java.awt.Color;

public class Route {
    private final RouteId lookup;
    private final String routeName;
    private final LocalDate createdAt;
    private final RouteSetter creator;
    private final Color tape;

    public Route(RouteId lookup,
                 String routeName,
                 LocalDate createdAt,
                 RouteSetter creator,
                 Color tape) {
        this.lookup = lookup;
        this.routeName = routeName;
        this.createdAt = createdAt;
        this.creator = creator;
        this.tape = tape;
    }

    @JsonProperty public RouteId getId() {
        return lookup;
    }

    @JsonProperty public String getRouteName() {
        return routeName;
    }

    @JsonProperty public LocalDate getCreationDate() {
        return createdAt;
    }

    @JsonProperty public RouteSetter getCreator() {
        return creator;
    }

    @JsonProperty public Color getTapeColor() {
        return tape;
    }
}
