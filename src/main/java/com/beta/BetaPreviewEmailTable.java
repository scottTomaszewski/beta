package com.beta;

import java.util.Arrays;

public enum BetaPreviewEmailTable implements BetaTable {
    ID("id", "int", "primary key auto_increment"),
    EMAIL("email", "varchar(100)"),
    DATE("date", "varchar(100)");

    public static final String TABLE_NAME = "beta_user";

    public static String creation() {
        return BetaTable.creation(TABLE_NAME, Arrays.stream(BetaUserTable.values()));
    }

    public final String columnName;
    private final String type;
    private final String extras;

    BetaPreviewEmailTable(String columnName, String type) {
        this(columnName, type, "");
    }

    BetaPreviewEmailTable(String columnName, String type, String extras) {
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
