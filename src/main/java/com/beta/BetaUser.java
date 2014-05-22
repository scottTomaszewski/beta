package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BetaUser {
    @JsonProperty
    private final int id;
    @JsonProperty
    private final String email;
    @JsonProperty
    private final String password;
    @JsonProperty
    private final OptionalInfo optionals;
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

        private OptionalInfo() {}

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
        private final CharBuffer salt;

        // needed for Jackson
        private OnlyForDeserialization() {
            this.salt = getSalt();
        }

        @VisibleForTesting
        public OnlyForDeserialization(String email, char[] plainTextPassword) {
            this.email = email;
            this.plainTextPassword = plainTextPassword;
            this.salt = getSalt();
        }

        public String email() {
            return email;
        }

        public String salt() {
            return salt.toString();
        }

        public String hashAndSaltPasswordThenClear() {
            Hasher h = Hashing.sha512().newHasher();
            salt.chars().forEach(h::putInt);
            CharBuffer.wrap(plainTextPassword).chars().forEach(h::putInt);
            String hashed = h.hash().toString();
            Arrays.fill(plainTextPassword, 'a');
            return hashed;
        }

        private CharBuffer getSalt() {
            if (salt == null) {
                try {
                    byte[] saltBuff = new byte[128];
                    SecureRandom.getInstanceStrong().nextBytes(saltBuff);
                    return ByteBuffer.wrap(saltBuff).asCharBuffer();
                } catch (NoSuchAlgorithmException e) {
                    throw new IllegalStateException(e);
                }
            }
            return salt;
        }
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaUser(r.getInt("id"),
                    r.getString("email"),
                    r.getString("password"),
                    r.getString("salt"),
                    OptionalInfo.map(r));
        }
    }
}
