package com.beta;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaUserMapper implements ResultSetMapper<BetaUser> {
    public BetaUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new BetaUser(r.getInt("id"), r.getString("name"));
    }
}
