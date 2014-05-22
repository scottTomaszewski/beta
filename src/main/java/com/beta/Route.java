package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Route {
    @JsonProperty
    private final int id;
    @JsonProperty
    private final BaseInfo info;
    @JsonProperty
    private final OptionalInfo optionals;

    private Route(int id, BaseInfo info, OptionalInfo optionals) {
        this.id = id;
        this.info = info;
        this.optionals = optionals;
    }

    public static final class BaseInfo {
        public static BaseInfo map(ResultSet r) throws SQLException {
            return new BaseInfo(r.getString("name"), Grade.from(r.getString("grade")));
        }

        @JsonProperty
        private String name;
        @JsonProperty
        private Grade grade;

        // needed for Jackson
        private BaseInfo() {}

        public BaseInfo(String name, Grade grade) {
            this.name = name;
            this.grade = grade;
        }

        public String name() { return name; }
        public Grade grade() { return grade; }
    }

    public static final class OptionalInfo {
        public static OptionalInfo map() {
            return new OptionalInfo();
        }

        @JsonProperty
        private String setter;

        private OptionalInfo() {}
    }

//    private final RouteId lookup;
//    private final String routeName;
//    private final LocalDate createdAt;
//    private final RouteSetter creator;
//    private final Color tape;

    public static class Mapper implements ResultSetMapper<Route> {
        public Route map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new Route(r.getInt("id"), BaseInfo.map(r), OptionalInfo.map());
        }
    }
}
