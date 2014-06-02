package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

// TODO - cleanup
public final class BetaUserCreation {
    @JsonProperty
    private String email;
    @JsonProperty
    private char[] plainTextPassword;
    private final CharBuffer salt;

    // needed for Jackson
    BetaUserCreation() {
        this.salt = getSalt();
    }

    @VisibleForTesting
    public BetaUserCreation(String email, char[] plainTextPassword) {
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
        String hash = new PasswordSecurity(plainTextPassword, salt).secure();
        Arrays.fill(plainTextPassword, 'a');
        return hash;
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
