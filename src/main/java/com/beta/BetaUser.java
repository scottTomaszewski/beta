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
    public final BaseInfo info;

    public BetaUser(int id, BaseInfo info) {
        this.id = id;
        this.info = info;
    }

    public String firstName() {
        return info.firstName;
    }

    public String lastName() {
        return info.lastName;
    }

    public static class BaseInfo {
        @JsonProperty public String firstName;
        @JsonProperty public String lastName;
        @JsonProperty public String email;
        @JsonProperty public String passwordHash;

        // needed for deserialization
        public BaseInfo() {}

        public BaseInfo(String firstName, String lastName, String email, String passwordHash) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.passwordHash = passwordHash;
        }

        public String firstName() {
            return firstName;
        }

        public String lastName() {
            return lastName;
        }

        public String email() {
            return email;
        }

        public boolean validatePassword(String hashed) {
            return hashed.equals(this.passwordHash);
        }
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new BetaUser(r.getInt("id"),
                    new BaseInfo(r.getString("firstName"),
                            r.getString("lastName"),
                            r.getString("email"),
                            r.getString("passwordHash")));
        }
    }
}
