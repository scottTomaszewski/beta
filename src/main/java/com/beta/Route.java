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
    public Route(@JsonProperty("id") int id, @JsonProperty("info") BaseInfo info, @JsonProperty("optionals") OptionalInfo optionals) {
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
            OptionalInfo from = new OptionalInfo();
            from.setterId = Optional.fromNullable(r.getInt(RouteTable.SETTER_ID.columnName));
            from.tapeColor = Optional.fromNullable(r.getInt(RouteTable.TAPE_COLOR.columnName));
            return from;
        }

        @JsonProperty
        private Optional<Integer> setterId = Optional.absent();
        @JsonProperty
        private Optional<Integer> tapeColor = Optional.absent();

        private OptionalInfo() {
        }

        @VisibleForTesting
        public OptionalInfo(Optional<Integer> setterId, Optional<Integer> tapeColor) {
            this.setterId = setterId;
            this.tapeColor = tapeColor;
        }

        public Optional<Integer> getSetterId() {
            return setterId;
        }

        public Optional<Integer> getTapeColor() {
            return tapeColor;
        }

        private void setSetterId(int setterId) {
            this.setterId = Optional.fromNullable(setterId);
        }

        private void setTapeColor(int tapeColor) {
            this.tapeColor = Optional.fromNullable(tapeColor);
        }
    }

//    private final LocalDate createdAt;

    public static class Mapper implements ResultSetMapper<Route> {
        public Route map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new Route(r.getInt("id"), BaseInfo.map(r), OptionalInfo.map(r));
        }
    }
}
