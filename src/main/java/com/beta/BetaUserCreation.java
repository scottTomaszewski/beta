package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public final class BetaUserCreation {
    public final String email;
    public final char[] plainTextPassword;

    // Lazy
    private String salt;

    @VisibleForTesting
    public BetaUserCreation(@JsonProperty("email") String email,
                            @JsonProperty("plainTextPassword") char[] plainTextPassword) {
        this.email = email;
        this.plainTextPassword = plainTextPassword;
    }

    public String email() {
        return email;
    }

    public String salt() { return getSalt(); }

    public String hashAndSaltPasswordThenClear() {
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
