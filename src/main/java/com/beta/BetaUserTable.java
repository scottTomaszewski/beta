package com.beta;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;

public enum BetaUserTable implements BetaTable {
    ID("id", "int", "primary key auto_increment"),
    EMAIL("email", "varchar(100)"),
    PASSWORD("password", "varchar(128)"),
    SALT("salt", "varchar(128)"),
    FIRST_NAME("firstName", "varchar(100)"),
    LAST_NAME("lastName", "varchar(100)"),
    PROFILE_PICTURE_ABSOLUTE_PATH("profilePictureAbsolutePath", "varchar(1024)");

    public static final String TABLE_NAME = "beta_user";

    public static String creation() {
        return BetaTable.creation(TABLE_NAME, Arrays.stream(BetaUserTable.values()));
    }

    public final String columnName;
    private final String type;
    private final String extras;

    BetaUserTable(String columnName, String type) {
        this(columnName, type, "");
    }

    BetaUserTable(String columnName, String type, String extras) {
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
