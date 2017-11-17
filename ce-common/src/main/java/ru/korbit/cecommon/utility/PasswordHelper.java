package ru.korbit.cecommon.utility;

import com.lambdaworks.crypto.SCryptUtil;
import lombok.val;

import java.security.SecureRandom;

public abstract class PasswordHelper {

    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();
    private static final int LENGTH = 6;

    private static final int N = 16384;
    private static final int R = 8;
    private static final int P = 1;

    public static String hashPassword(final String password) {
        return SCryptUtil.scrypt(password, N, R, P);
    }

    public static boolean checkPassword(final String password, final String hash) {
        return SCryptUtil.check(password, hash);
    }

    public static String generate() {
        val random = new SecureRandom();
        val verifierBytes = new byte[LENGTH];
        random.nextBytes(verifierBytes);
        return getAuthorizationCodeString(verifierBytes);
    }

    private static String getAuthorizationCodeString(byte[] verifierBytes) {
        val chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }
}
