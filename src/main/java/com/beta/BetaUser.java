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
    private final BaseInfo info;
    @JsonProperty
    private final OptionalInfo optionals;

    public BetaUser(int id, BaseInfo info, OptionalInfo optionals) {
        this.id = id;
        this.info = info;
        this.optionals = optionals;
    }

    public int id() {
        return id;
    }

    public BaseInfo info() {
        return info;
    }

    public OptionalInfo optionals() {
        return optionals;
    }

    public static final class BaseInfo {
        public static BaseInfo map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BaseInfo(r.getString("email"), r.getString("passwordHash"));
        }

        @JsonProperty
        private String email;
        @JsonProperty
        private String passwordHash;

        // needed for Jackson
        public BaseInfo() {
        }

        public BaseInfo(String email, String passwordHash) {
            this.email = email;
            this.passwordHash = passwordHash;
        }

        public String email() {
            return email;
        }

        public String passwordHash() {
            return passwordHash;
        }

        public boolean validatePassword(String hashed) {
            return hashed.equals(this.passwordHash);
        }
    }

    public static final class OptionalInfo {
        private static OptionalInfo map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new OptionalInfo()
                    .setFirstName(r.getString("firstName"))
                    .setLastName(r.getString("lastName"))
                    .setProfilePictureAbsolutePath(r.getString("profilePictureAbsolutePath"));
        }

        @JsonProperty
        private String firstName;
        @JsonProperty
        private String lastName;
        @JsonProperty
        private String profilePictureRelativePath;

        public OptionalInfo() {}

        public String getProfilePictureAbsolutePath() {
            return Strings.nullToEmpty(profilePictureRelativePath);
        }

        public OptionalInfo setProfilePictureAbsolutePath(String path) {
            this.profilePictureRelativePath = path;
            return this;
        }

        public String getFirstName() {
            return Strings.nullToEmpty(firstName);
        }

        public OptionalInfo setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return Strings.nullToEmpty(lastName);
        }

        public OptionalInfo setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaUser(r.getInt("id"), BaseInfo.map(idx, r, c), OptionalInfo.map(idx, r, c));
        }
    }
}
