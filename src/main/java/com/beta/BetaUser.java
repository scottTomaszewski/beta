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
    public final String firstName;
    @JsonProperty
    public final String lastName;

    public BetaUser(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName(){
        return firstName;
    }

    public String lastName(){
        return lastName;
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new BetaUser(r.getInt("id"), r.getString("firstName"), r.getString("lastName"));
        }
    }
}
