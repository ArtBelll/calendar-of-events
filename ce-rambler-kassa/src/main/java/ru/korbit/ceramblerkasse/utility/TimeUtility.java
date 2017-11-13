package ru.korbit.ceramblerkasse.utility;

import lombok.val;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by Artur Belogur on 16.10.17.
 */
public abstract class TimeUtility {

    private TimeUtility() {}

    public static Duration durationFromString(String duration) {
        val index = duration.indexOf(" ");
        val minutes = index == -1 ? 0 : Integer.parseInt(duration.subSequence(0, index).toString());
        return Duration.ofMinutes(minutes);
    }

    public static ZonedDateTime getMaxDate(ZoneOffset offset) {
        return ZonedDateTime.now(offset).plusWeeks(1);
    }
}
