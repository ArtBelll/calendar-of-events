package ru.korbit.cecommon.utility;

import java.time.LocalDate;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public final class DateTimeUtils {

    private DateTimeUtils() {}

    public static boolean isAfterOrEqual(LocalDate d1, LocalDate d2) {
        return d1.isAfter(d2) || d1.isEqual(d2);
    }

    public static boolean isBeforeOrEqual(LocalDate d1, LocalDate d2) {
        return d1.isBefore(d2) || d1.isEqual(d2);
    }
}
