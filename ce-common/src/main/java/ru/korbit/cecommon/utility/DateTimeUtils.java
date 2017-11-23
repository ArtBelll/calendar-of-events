package ru.korbit.cecommon.utility;

import lombok.val;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public final class DateTimeUtils {

    private DateTimeUtils() {}

    public static Comparator<ZonedDateTime> zonedDateTimeComparator = (d1, d2) -> {
        if (d1.isEqual(d2)) return 0;
        return d1.isBefore(d2) ? -1 : 1;
    };

    public static boolean isAfterOrEqual(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.isAfter(d2) || d1.isEqual(d2);
    }

    public static boolean isBeforeOrEqual(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.isBefore(d2) || d1.isEqual(d2);
    }

    public static Stream<Long> getListOfDayInSecondsBetween(ZonedDateTime start, ZonedDateTime finish) {
        val days = ChronoUnit.DAYS.between(LocalDate.from(start), LocalDate.from(finish));
        return LongStream.range(0, days + 1)
                .boxed()
                .map(i -> TimeUnit.SECONDS.convert(LocalDate.from(start).plusDays(i).toEpochDay(), TimeUnit.DAYS));
    }

    public static Long getExpireMillis(ZonedDateTime endTime) {
        return Duration.between(ZonedDateTime.now(ZoneId.systemDefault()), endTime).abs().toMillis();
    }

    public static boolean dateInDates(List<ZonedDateTime> dates, ZonedDateTime date) {
        return dates.parallelStream()
                .anyMatch(currentDate -> currentDate.truncatedTo(ChronoUnit.DAYS)
                        .equals(date.truncatedTo(ChronoUnit.DAYS)));
    }
}
