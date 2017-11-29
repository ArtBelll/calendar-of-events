package ru.korbit.cecommon.utility;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import lombok.val;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.cronutils.model.CronType.QUARTZ;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public final class DateTimeUtils {

    public static final CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));

    private DateTimeUtils() {}

    public static Comparator<ZonedDateTime> zonedDateComparator = (d1, d2) -> {
        if (compareDays(d1, d2)) return 0;
        return d1.isBefore(d2) ? -1 : 1;
    };

    public static boolean isAfterOrEqual(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.isAfter(d2) || d1.isEqual(d2);
    }

    public static boolean isBeforeOrEqual(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.isBefore(d2) || d1.isEqual(d2);
    }

    public static Stream<ZonedDateTime> getListOfDayBetween(ZonedDateTime start, ZonedDateTime finish) {
        val days = ChronoUnit.DAYS.between(start, finish);
        val offset = start.getOffset().getTotalSeconds();
        return LongStream.range(0, days + 1)
                .boxed()
                .map(i -> start.plusDays(i).plusSeconds(offset));
    }

    public static Long getExpireMillis(ZonedDateTime endTime) {
        return Duration.between(ZonedDateTime.now(ZoneId.systemDefault()), endTime).abs().toMillis();
    }

    public static boolean dateInDates(List<ZonedDateTime> dates, ZonedDateTime date) {
        return dates.parallelStream()
                .anyMatch(currentDate -> compareDays(currentDate, date));
    }

    public static boolean compareDays(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.truncatedTo(ChronoUnit.DAYS).isEqual(d2.truncatedTo(ChronoUnit.DAYS));
    }
}
