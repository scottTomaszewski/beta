package com.beta;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;

public enum BetaUserTable {
    ID("id", "int", "primary key auto_increment"),
    EMAIL("email", "varchar(100)"),
    PASSWORD("password", "varchar(128)"),
    SALT("salt", "varchar(128)"),
    FIRST_NAME("firstName", "varchar(100)"),
    LAST_NAME("lastName", "varchar(100)"),
    PROFILE_PICTURE_ABSOLUTE_PATH("profilePictureAbsolutePath", "varchar(1024)");

    public static final String TABLE_NAME = "beta_user";

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

    public static String creation() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(TABLE_NAME);
        sb.append(" (");
        Arrays.stream(BetaUserTable.values()).forEach(column -> sb.append(column.create()));
        // remove last ", "
        sb.delete(sb.length()-2, sb.length());
        sb.append(")");
        return sb.toString();
    }

    @VisibleForTesting
    String create() {
        return String.format("%s %s%s, ", columnName, type, extras.isEmpty() ? "": " " + extras);
    }
}
