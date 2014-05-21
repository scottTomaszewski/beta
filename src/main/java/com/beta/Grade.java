package com.beta;

public interface Grade {
    default Grade from(String toParse) {
        if (BoulderingGrade.contains(toParse)) {
            return BoulderingGrade.valueOf(toParse);
        } else if (RopeGrade.contains(toParse)) {
            return RopeGrade.valueOf(toParse);
        } else {
            throw new IllegalArgumentException(toParse + " is not a recognized grade");
        }
    }
}
