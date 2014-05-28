package com.beta;


import java.util.Optional;

public enum BoulderingGrade implements Grade {
    VIntro("VIntro", 0),
    VIntro_PLUS("VIntro+", 0.5),
    V0("V0", 0),
    V0_PLUS("V0+", 0.5),
    V1("V1", 1),
    V1_PLUS("V1+", 1.5),
    V2("V2", 2),
    V2_PLUS("V2+", 2.5),
    V3("V3", 3),
    V3_PLUS("V3+", 3.5),
    V4("V4", 4),
    V4_PLUS("V4+", 4.5),
    V5("V5", 5),
    V5_PLUS("V5+", 5.5),
    V6("V6", 6),
    V6_PLUS("V6+", 6.5),
    V7("V7", 7),
    V7_PLUS("V7+", 7.5),
    V8("V8", 8),
    V8_PLUS("V8+", 8.5),
    V9("V9", 9),
    V9_PLUS("V9+", 9.5),
    V10("V10", 10),
    V10_PLUS("V10+", 10.5),
    V11("V11", 11),
    V11_PLUS("V11+", 11.5),
    V12("V12", 12),
    V12_PLUS("V12+", 12.5),
    V13("V13", 13),
    V13_PLUS("V13+", 13.5),
    V14("V14", 14),
    V14_PLUS("V14+", 14.5),
    V15("V15", 15),
    V15_PLUS("V15+", 15.5),
    V16("V16", 16),
    V16_PLUS("V16+", 16.5),
    V17("V17", 17),
    V17_PLUS("V17+", 17.5);

    private final String value;
    private final double rank;

    BoulderingGrade(String value, double rank) {
        this.value = value;
        this.rank = rank;
    }

    public String getValue() {
        return value;
    }

    public double getRank() {
        return rank;
    }

    public static Optional<BoulderingGrade> fromValue(String toLookup) {
        for (BoulderingGrade val : values()) {
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
