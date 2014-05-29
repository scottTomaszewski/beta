package com.beta;

import com.google.common.annotations.VisibleForTesting;

import java.util.stream.Stream;

public interface BetaTable {
    public static <T extends BetaTable> String creation(String tableName, Stream<T> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableName);
        sb.append(" (");
        columns.forEach(column -> sb.append(column.create()));
        // remove last ", "
        sb.delete(sb.length()-2, sb.length());
        sb.append(")");
        return sb.toString();
    }

    String columnName();
    String type();
    String extras();

    @VisibleForTesting
    default String create() {
        return String.format("%s %s%s, ", columnName(), type(), extras().isEmpty() ? "": " " + extras());
    }
}
