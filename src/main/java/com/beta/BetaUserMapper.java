package com.beta;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaUserMapper implements ResultSetMapper<Main.BetaUser> {
    public Main.BetaUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Main.BetaUser(r.getInt("id"), r.getString("name"));
    }
}
