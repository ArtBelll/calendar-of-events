package ru.korbit.cecommon.utility;

import lombok.val;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.packet.DateRange;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public final class DateTimeUtils {

    private DateTimeUtils() {}

    public static boolean isAfterOrEqual(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.isAfter(d2) || d1.isEqual(d2);
    }

    public static boolean isBeforeOrEqual(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.isBefore(d2) || d1.isEqual(d2);
    }

    public static Stream<Long> getListOfDayInSecondsBetween(ZonedDateTime start, ZonedDateTime finish) {
        val days = ChronoUnit.DAYS.between(start, finish);
        return LongStream.range(0, days + 1)
                .boxed()
                .map(i -> start.plusDays(i).toEpochSecond());
    }

    public static List<DateRange> getActiveDateRanges(Stream<Event> events, ZonedDateTime endRange) {
        val activeDaysInDataRanges = new ArrayList<DateRange>();
        events.forEach(event -> {
            val finish = event.getFinishDay().isBefore(endRange) ? event.getFinishDay() : endRange;

            if (activeDaysInDataRanges.isEmpty()) {
                activeDaysInDataRanges.add(new DateRange(event.getStartDay(), finish));
                return;
            }
            val index = activeDaysInDataRanges.size() - 1;
            val lastRange = activeDaysInDataRanges.get(index);

            if (DateTimeUtils.isBeforeOrEqual(event.getStartDay(), lastRange.getFinish())) {
                if (lastRange.getFinish().isBefore(event.getFinishDay())) {
                    lastRange.setFinish(finish);
                }
            } else {
                activeDaysInDataRanges.add(new DateRange(event.getStartDay(), finish));
            }
        });

        return activeDaysInDataRanges;
    }

    public static Long getExpireMillis(ZonedDateTime endTime) {
        return Math.abs(Duration.between(ZonedDateTime.now(ZoneId.systemDefault()), endTime).toMillis());
    }
}
