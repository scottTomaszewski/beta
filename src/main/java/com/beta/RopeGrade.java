package com.beta;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum RopeGrade implements Grade {
    _5_INTRO("5.Intro", 0),
    _5_INTRO_PLUS("5.Intro+", 0.5),
    _5_0("5.0", 0),
    _5_0_PLUS("5.0+", 0.5),
    _5_1("5.1", 1),
    _5_1_PLUS("5.1+", 1.5),
    _5_2("5.2", 2),
    _5_2_PLUS("5.2+", 2.5),
    _5_3("5.3", 3),
    _5_3_PLUS("5.3+", 3.5),
    _5_4("5.4", 4),
    _5_4_PLUS("5.4+", 4.5),
    _5_5("5.5", 5),
    _5_5_PLUS("5.5+", 5.5),
    _5_6("5.6", 6),
    _5_6_PLUS("5.6+", 6.5),
    _5_7("5.7", 7),
    _5_7_PLUS("5.7+", 7.5),
    _5_8("5.8", 8),
    _5_8_PLUS("5.8+", 8.5),
    _5_9("5.9", 9),
    _5_9_PLUS("5.9+", 9.5),

    _5_10a("5.10a", 10),
    _5_10a_PLUS("5.10a+", 10.5),
    _5_10b("5.10b", 11),
    _5_10b_PLUS("5.10b+", 11.5),
    _5_10c("5.10c", 12),
    _5_10c_PLUS("5.10c+", 12.5),
    _5_10d("5.10d", 13),
    _5_10d_PLUS("5.10d+", 13.5),

    _5_11a("5.11a", 14),
    _5_11a_PLUS("5.11a+", 14.5),
    _5_11b("5.11b", 15),
    _5_11b_PLUS("5.11b+", 15.5),
    _5_11c("5.11c", 16),
    _5_11c_PLUS("5.11c+", 16.5),
    _5_11d("5.11d", 17),
    _5_11d_PLUS("5.11d+", 17.5),

    _5_12a("5.12", 18),
    _5_12a_PLUS("5.12a+", 18.5),
    _5_12b("5.12", 19),
    _5_12b_PLUS("5.12b+", 19.5),
    _5_12c("5.12", 20),
    _5_12c_PLUS("5.12c+", 20.5),
    _5_12d("5.12", 21),
    _5_12d_PLUS("5.12d+", 21.5),

    _5_13a("5.13a", 22),
    _5_13a_PLUS("5.13a+", 22.5),
    _5_13b("5.13b", 23),
    _5_13b_PLUS("5.13b+", 23.5),
    _5_13c("5.13c", 24),
    _5_13c_PLUS("5.13c+", 24.5),
    _5_13d("5.13d", 25),
    _5_13d_PLUS("5.13d+", 25.5),

    _5_14a("5.14a", 26),
    _5_14a_PLUS("5.14a+", 26.5),
    _5_14b("5.14b", 27),
    _5_14b_PLUS("5.14b+", 27.5),
    _5_14c("5.14c", 28),
    _5_14c_PLUS("5.14c+", 28.5),
    _5_14d("5.14d", 29),
    _5_14d_PLUS("5.14d+", 29.5),

    _5_15a("5.15a", 30),
    _5_15a_PLUS("5.15a+", 30.5),
    _5_15b("5.15b", 31),
    _5_15b_PLUS("5.15b+", 31.5),
    _5_15c("5.15c", 32),
    _5_15c_PLUS("5.15c+", 32.5),
    _5_15d("5.15d", 33),
    _5_15d_PLUS("5.15d+", 33.5),

    _5_16a("5.16a", 34),
    _5_16a_PLUS("5.16a+", 34.5),
    _5_16b("5.16b", 35),
    _5_16b_PLUS("5.16b+", 35.5),
    _5_16c("5.16c", 36),
    _5_16c_PLUS("5.16c+", 36.5),
    _5_16d("5.16d", 37),
    _5_16d_PLUS("5.16d+", 37.5);

    private final String value;
    private final double rank;

    RopeGrade(String value, double rank) {
        this.value = value;
        this.rank = rank;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public double getRank() {
        return rank;
    }

    public static Optional<RopeGrade> fromValue(String toLookup) {
        for (RopeGrade val : values()) {
            if (val.value.equals(toLookup)) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return value;
    }
}
