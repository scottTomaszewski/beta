package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaUser {
    @JsonProperty
    public final int id;
    @JsonProperty
    public final String name;

    public BetaUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new BetaUser(r.getInt("id"), r.getString("name"));
        }
    }
}
