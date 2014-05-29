package com.beta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Route {
    public final int id;
    public final BaseInfo info;
    public final OptionalInfo optionals;

    @VisibleForTesting
    @JsonCreator
    public Route(@JsonProperty("id") int id,
                 @JsonProperty("info") BaseInfo info,
                 @JsonProperty("optionals") OptionalInfo optionals) {
        this.id = id;
        this.info = info;
        this.optionals = optionals;
    }

    public static final class BaseInfo {
        public static BaseInfo map(ResultSet r) throws SQLException {
            return new BaseInfo(r.getString("name"), Grade.from(r.getString("grade")));
        }

        public final String name;
        public final Grade grade;

        @JsonCreator
        public BaseInfo(@JsonProperty("name") String name, @JsonProperty("grade") Grade grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    public static final class OptionalInfo {
        public static OptionalInfo map(ResultSet r) throws SQLException {
            return new OptionalInfo(
                    Optional.fromNullable(r.getString(RouteTable.SETTER_ID.columnName)),
                    Optional.fromNullable(r.getString(RouteTable.TAPE_COLOR.columnName)));
        }

        public final Optional<String> setterId;
        public final Optional<String> tapeColor;

        @VisibleForTesting
        @JsonCreator
        public OptionalInfo(@JsonProperty("setterId") Optional<String> setterId,
                            @JsonProperty("tapeColor") Optional<String> tapeColor) {
            this.setterId = setterId;
            this.tapeColor = tapeColor;
        }
    }

//    private final LocalDate createdAt;

    public static class Mapper implements ResultSetMapper<Route> {
        public Route map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new Route(r.getInt("id"), BaseInfo.map(r), OptionalInfo.map(r));
        }
    }
}
