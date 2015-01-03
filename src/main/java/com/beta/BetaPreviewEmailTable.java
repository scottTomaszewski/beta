package com.beta;

import java.util.Arrays;

public enum BetaPreviewEmailTable implements BetaTable {
    ID("id", "int", "primary key auto_increment"),
    EMAIL("email", "varchar(200)"),
    DATE("date", "varchar(200)");

    public static final String TABLE_NAME = "preview_email";

    public static String creation() {
        return BetaTable.creation(TABLE_NAME, Arrays.stream(values()));
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
