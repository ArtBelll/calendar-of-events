package ru.korbit.cecommon.utility;

import com.lambdaworks.crypto.SCryptUtil;

public class PasswordHasher {
    private static final int N = 16384;
    private static final int R = 8;
    private static final int P = 1;

    public static String hashPassword(final String password) {
        return SCryptUtil.scrypt(password, N, R, P);
    }

    public static boolean checkPassword(final String password, final String hash) {
        return SCryptUtil.check(password, hash);
    }

}
