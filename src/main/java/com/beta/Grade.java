package com.beta;

import java.util.Optional;

public interface Grade {
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
