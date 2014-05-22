package com.beta;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Optional;

public interface Grade {
    @JsonCreator
    static Grade from(String toParse) {
        Optional<BoulderingGrade> maybe = BoulderingGrade.fromValue(toParse);
        if (maybe.isPresent()) {
            return maybe.get();
        }
        Optional<RopeGrade> maybeRope = RopeGrade.fromValue(toParse);
        if (maybeRope.isPresent()) {
            return maybeRope.get();
        }
        throw new IllegalArgumentException(toParse + " is not a recognized grade");
    }

    String getValue();

    double getRank();
}
