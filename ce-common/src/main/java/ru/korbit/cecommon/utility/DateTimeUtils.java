package ru.korbit.cecommon.utility;

import lombok.val;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.packet.DateRange;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public final class DateTimeUtils {

    public static int HOURS_IN_DAY = 24;
    public static int MINUTES_IN_HOURS = 60;
    public static int SECONDS_IN_MINUTES = 60;
    public static int MILLISECONDS_IN_SECONDS = 1000;

    private DateTimeUtils() {}

    public static boolean isAfterOrEqual(LocalDate d1, LocalDate d2) {
        return d1.isAfter(d2) || d1.isEqual(d2);
    }

    public static boolean isBeforeOrEqual(LocalDate d1, LocalDate d2) {
        return d1.isBefore(d2) || d1.isEqual(d2);
    }

    public static LocalDate epochSecondToLocalDate(Long secs) {
        return Instant.ofEpochSecond(secs).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Stream<Long> getListOfDayInSecondsBetween(LocalDate start, LocalDate finish) {
        val days = ChronoUnit.DAYS.between(start, finish);
        return LongStream.range(0, days + 1)
                .boxed()
                .map(i -> start.plusDays(i).toEpochDay() * HOURS_IN_DAY * MINUTES_IN_HOURS * SECONDS_IN_MINUTES);
    }

    public static List<DateRange> getActiveDateRanges(Stream<Event> events, LocalDate endRange) {
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
}
