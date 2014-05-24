package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaUser {
    @JsonProperty
    private final int id;
    @JsonProperty
    private final String email;
    @JsonProperty
    private final OptionalInfo optionals;

    private final String password;
    private final String salt;

    private BetaUser(int id, String email, String password, String salt, OptionalInfo optionals) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.optionals = optionals;
    }

    public static final class OptionalInfo {
        private static OptionalInfo map(ResultSet r) throws SQLException {
            OptionalInfo data = new OptionalInfo();
            data.firstName = r.getString(BetaUserTable.FIRST_NAME.columnName);
            data.lastName = r.getString(BetaUserTable.LAST_NAME.columnName);
            data.profilePictureAbsolutePath =
                    r.getString(BetaUserTable.PROFILE_PICTURE_ABSOLUTE_PATH.columnName);
            return data;
        }

        @JsonProperty
        private String firstName;
        @JsonProperty
        private String lastName;
        @JsonProperty
        private String profilePictureAbsolutePath;

        private OptionalInfo() {}

        public String getFirstName() {
            return Strings.nullToEmpty(firstName);
        }

        public String getLastName() {
            return Strings.nullToEmpty(lastName);
        }
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaUser(r.getInt(BetaUserTable.ID.columnName),
                    r.getString(BetaUserTable.EMAIL.columnName),
                    r.getString(BetaUserTable.PASSWORD.columnName),
                    r.getString(BetaUserTable.SALT.columnName),
                    OptionalInfo.map(r));
        }
    }
}
