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

    // Lazy
    private String salt;

    // needed for Jackson
    BetaUserCreation() {}

    @VisibleForTesting
    public BetaUserCreation(String email, char[] plainTextPassword) {
        this.email = email;
        this.plainTextPassword = plainTextPassword;
    }

    public String email() {
        return email;
    }

    public String salt() { return getSalt(); }

    public String hashAndSaltPasswordThenClear() {
        System.out.println(getSalt());
        String hash = new PasswordSecurity(plainTextPassword, getSalt()).secure();
        Arrays.fill(plainTextPassword, 'a');
        return hash;
    }

    private String getSalt() {
        if (salt == null) {
            try {
                byte[] saltBuff = new byte[128];
                SecureRandom.getInstanceStrong().nextBytes(saltBuff);
                salt = ByteBuffer.wrap(saltBuff).asCharBuffer().toString();
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e);
            }
        }
        return salt;
    }
}
