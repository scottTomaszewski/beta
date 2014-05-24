package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
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
            data.firstName = Optional.fromNullable(r.getString(BetaUserTable.FIRST_NAME.columnName));
            data.lastName = Optional.fromNullable(r.getString(BetaUserTable.LAST_NAME.columnName));
            data.pictureAbsolutePath = Optional.fromNullable(
                    r.getString(BetaUserTable.PICTURE_ABSOLUTE_PATH.columnName));
            return data;
        }

        @JsonProperty
        private Optional<String> firstName = Optional.absent();
        @JsonProperty
        private Optional<String> lastName = Optional.absent();
        @JsonProperty
        private Optional<String> pictureAbsolutePath = Optional.absent();

        private OptionalInfo() {
        }

        public Optional<String> getFirstName() {
            return firstName;
        }

        public Optional<String> getLastName() { return lastName; }

        public Optional<String> getPictureAbsolutePath() { return pictureAbsolutePath; }
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
