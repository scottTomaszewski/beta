package com.beta;

import java.util.Arrays;

public enum RouteTable implements BetaTable {
    ID("id", "int", "primary key auto_increment"),
    NAME("name", "varchar(100)"),
    GRADE("grade", "varchar(100)"),
    SETTER_ID("setterId", "int"),
    TAPE_COLOR("tapeColor", "varchar(100)");

    public static final String TABLE_NAME = "route";

    public static String creation() {
        return BetaTable.creation(TABLE_NAME, Arrays.stream(RouteTable.values()));
    }

    public final String columnName;
    private final String type;
    private final String extras;

    RouteTable(String columnName, String type) {
        this(columnName, type, "");
    }

    RouteTable(String columnName, String type, String extras) {
        this.columnName = columnName;
        this.type = type;
        this.extras = extras;
    }

    public String columnName() {
        return columnName;
    }

    public String type() {
        return type;
    }

    public String extras() {
        return extras;
    }
}
