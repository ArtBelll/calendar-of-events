package ru.korbit.cecommon.utility;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public final class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    public static String getSqlPatternInAnyPosition(String input) {
        return "%" + input.toLowerCase() + "%";
    }
}
