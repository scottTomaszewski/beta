package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Chars;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.nio.CharBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class BetaUser {
    @JsonProperty
    private final int id;
    @JsonProperty
    private final String email;
    @JsonProperty
    private final String hashedPassword;
    @JsonProperty
    private final OptionalInfo optionals;

    private BetaUser(int id, String email, String hashedPassword, OptionalInfo optionals) {
        this.id = id;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.optionals = optionals;
    }

    public static final class OptionalInfo {
        private static OptionalInfo map(ResultSet r) throws SQLException {
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
        private String profilePictureAbsolutePath;

        public OptionalInfo() {}

        public OptionalInfo setProfilePictureAbsolutePath(String path) {
            this.profilePictureAbsolutePath = path;
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

    public static final class OnlyForDeserialization {
        @JsonProperty
        private String email;
        @JsonProperty
        private char[] plainTextPassword;

        // needed for Jackson
        public OnlyForDeserialization() {}

        @VisibleForTesting
        public OnlyForDeserialization(String email, char[] plainTextPassword) {
            this.email = email;
            this.plainTextPassword = plainTextPassword;
        }

        public String email() {
            return email;
        }

        public String hashPasswordAndClear() {
            //TODO salt the hash
            Hasher h = Hashing.sha512().newHasher();
            CharBuffer.wrap(plainTextPassword).chars().forEach(h::putInt);
            String hashed = h.hash().toString();

            // erase from memory
            Arrays.fill(plainTextPassword, 'a');
            return hashed;
        }
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaUser(r.getInt("id"),
                    r.getString("email"),
                    r.getString("hashedPassword"),
                    OptionalInfo.map(r));
        }
    }
}
