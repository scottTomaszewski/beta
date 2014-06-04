package com.beta;

import com.google.common.base.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Route extends RouteDTO {
    public Route(int id, String name, Grade grade,
                 Optional<String> setterId, Optional<String> tapeColor) {
        super(id, name, grade, setterId, tapeColor);
    }

    public RouteDTO asDTO() {
        return this;
    }

    public static class Mapper implements ResultSetMapper<RouteDTO> {
        public Route map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new Route(r.getInt("id"),
                    r.getString("name"),
                    Grade.from(r.getString("grade")),
                    Optional.fromNullable(r.getString(RouteTable.SETTER_ID.columnName)),
                    Optional.fromNullable(r.getString(RouteTable.TAPE_COLOR.columnName)));
        }
    }
}
