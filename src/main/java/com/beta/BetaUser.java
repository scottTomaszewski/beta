package com.beta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaUser {
    public final int id;
    public final String email;
    public final OptionalInfo optionals;

    // Not annotated since we dont want to send back to client
    private final String password;
    private final String salt;

    @VisibleForTesting
    BetaUser(@JsonProperty("id") int id,
             @JsonProperty("email") String email,
             @JsonProperty("password") String password,
             @JsonProperty("salt") String salt,
             @JsonProperty("optionals") OptionalInfo optionals) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.optionals = optionals;
    }

    public static final class OptionalInfo {
        private static OptionalInfo map(ResultSet r) throws SQLException {
            return new OptionalInfo(
                    Optional.fromNullable(r.getString(BetaUserTable.FIRST_NAME.columnName)),
                    Optional.fromNullable(r.getString(BetaUserTable.LAST_NAME.columnName)),
                    Optional.fromNullable(r.getString(BetaUserTable.PICTURE_ABSOLUTE_PATH.columnName)));
        }

        public final Optional<String> firstName;
        public final Optional<String> lastName;
        public final Optional<String> pictureAbsolutePath;

        @VisibleForTesting
        @JsonCreator
        OptionalInfo(@JsonProperty("firstName") Optional<String> firstName,
                     @JsonProperty("lastName") Optional<String> lastName,
                     @JsonProperty("pictureAbsolutePath") Optional<String> pictureAbsolutePath) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.pictureAbsolutePath = pictureAbsolutePath;
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
