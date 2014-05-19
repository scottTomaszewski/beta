package com.beta;

public enum BoulderingGrade {
    VIntro(0),
    VIntro_PLUS(0.5),
    V0(0),
    V0_PLUS(0.5),
    V1(1),
    V1_PLUS(1.5),
    V2(2),
    V2_PLUS(2.5),
    V3(3),
    V3_PLUS(3.5),
    V4(4),
    V4_PLUS(4.5),
    V5(5),
    V5_PLUS(5.5),
    V6(6),
    V6_PLUS(6.5),
    V7(7),
    V7_PLUS(7.5),
    V8(8),
    V8_PLUS(8.5),
    V9(9),
    V9_PLUS(9.5),
    V10(10),
    V10_PLUS(10.5),
    V11(11),
    V11_PLUS(11.5),
    V12(12),
    V12_PLUS(12.5),
    V13(13),
    V13_PLUS(13.5),
    V14(14),
    V14_PLUS(14.5),
    V15(15),
    V15_PLUS(15.5),
    V16(16),
    V16_PLUS(16.5),
    V17(17),
    V17_PLUS(17.5);

    private final double rank;

    BoulderingGrade(double rank) {
        this.rank = rank;
    }
}
