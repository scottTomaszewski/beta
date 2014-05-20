package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
            return new BaseInfo(r.getString("firstName"),
                    r.getString("lastName"),
                    r.getString("email"),
                    r.getString("passwordHash"));
        }

        @JsonProperty
        private String firstName;
        @JsonProperty
        private String lastName;
        @JsonProperty
        private String email;

        @JsonProperty
        private String passwordHash;

        // needed for Jackson
        public BaseInfo() {
        }

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

        public String passwordHash() {
            return passwordHash;
        }

        public boolean validatePassword(String hashed) {
            return hashed.equals(this.passwordHash);
        }
    }

    private static final class OptionalInfo {
        private static OptionalInfo map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new OptionalInfo();
        }

        @JsonProperty
        private String profilePictureRelativePath;

        public OptionalInfo() {}

        public Optional<String> getProfilePictureRelativePath() {
            return Optional.ofNullable(profilePictureRelativePath);
        }

        public void setProfilePictureRelativePath(String path) {
            this.profilePictureRelativePath = path;
        }
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaUser(r.getInt("id"), BaseInfo.map(idx, r, c), OptionalInfo.map(idx, r, c));
        }
    }
}
