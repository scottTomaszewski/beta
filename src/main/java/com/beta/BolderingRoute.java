package com.beta;

import org.joda.time.LocalDate;

import java.awt.*;

public class BolderingRoute extends Route {
    private final BoulderingGrade difficulty;

    public BolderingRoute(RouteId lookup,
                          String routeName,
                          LocalDate createdAt,
                          RouteSetter creator,
                          Color tape,
                          BoulderingGrade difficulty) {
        super(lookup, routeName, createdAt, creator, tape);
        this.difficulty = difficulty;
    }
}
