package com.beta;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.nio.CharBuffer;
import java.util.Arrays;

public class PasswordSecurity {
    private final char[] plaintext;
    private final CharBuffer salt;

    public PasswordSecurity(char[] plaintext, CharBuffer salt) {
        this.plaintext = plaintext;
        this.salt = salt;
    }

    public String secure() {
        return hashAndSalt();
    }

    public boolean checkAgainst(String expectedHash) {
        return hashAndSalt().equals(expectedHash);
    }

    private String hashAndSalt() {
        Hasher h = Hashing.sha512().newHasher();
        salt.chars().forEach(h::putInt);
        CharBuffer.wrap(plaintext).chars().forEach(h::putInt);
        String hashed = h.hash().toString();
        Arrays.fill(plaintext, 'a');
        return hashed;
    }
}
