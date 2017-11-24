package ru.korbit.cecommon.utility;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import lombok.val;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.domain.RecurringEvent;
import ru.korbit.cecommon.packet.EventCronIterator;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.cronutils.model.CronType.QUARTZ;

public class EventActionHelper {

    private final CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));

    private boolean isExistToday = false;

    private final Long cityId;
    private final ZonedDateTime from;
    private final ZonedDateTime to;

    public EventActionHelper(Long cityId, ZonedDateTime from, ZonedDateTime to) {
        this.cityId = cityId;
        this.from = from;
        this.to = to;
    }

    public Stream<ZonedDateTime> getSetActiveDays(Event event) {
        if (event instanceof CinemaEvent) {
            return this.getSetActiveDays((CinemaEvent) event);
        } else if (event instanceof RecurringEvent) {
            return getSetActiveDays((RecurringEvent) event);
        } else {
            throw new RuntimeException("Unknown event");
        }
    }

    private Stream<ZonedDateTime> getSetActiveDays(CinemaEvent cinemaEvent) {
        val schedule = cinemaEvent.getEventSchedules()
                .stream()
                .filter(eventSchedule -> eventSchedule.getCity().getId().equals(cityId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Event haven't schedule"));

        val start = schedule.getStart().isBefore(from) ? from : schedule.getStart();
        val finish = schedule.getFinish().isAfter(to) ? to : schedule.getFinish();

        boolean isCurrentDay = false;
        if (DateTimeUtils.compareDays(ZonedDateTime.now(), start) && !isExistToday) {
            isCurrentDay = isExistToday = cinemaEvent.getShowtimeList()
                    .stream()
                    .anyMatch(showtime -> showtime.getStartTime().isBefore(start.plusDays(1))
                            && showtime.getStartTime().isAfter(start));

        }

        val realStart = isCurrentDay ? start : start.plusDays(1);
        return DateTimeUtils.getListOfDayBetween(realStart.truncatedTo(ChronoUnit.DAYS), finish);
    }

    private Stream<ZonedDateTime> getSetActiveDays(RecurringEvent recurringEvent) {
        Stream<ZonedDateTime> resultSet = recurringEvent.getActionSchedules()
                .parallelStream()
                .filter(actionSchedule -> actionSchedule.getCity().getId().equals(cityId))
                .map(action -> {
                    val cron = ExecutionTime.forCron(parser.parse(action.getCron()));
                    return getSetActiveDaysInCron(cron, action.getDuration());
                })
                .flatMap(set -> set.map(date -> date.truncatedTo(ChronoUnit.DAYS)))
                .filter(date -> !DateTimeUtils.dateInDates(recurringEvent.getNoneAction(), date)
                        && !DateTimeUtils.dateInDates(recurringEvent.getDatesException(), date));

        val dateExceptions = new HashSet<ZonedDateTime>();
        recurringEvent.getDatesException().forEach(date -> {
            if (date.isBefore(to) && date.isAfter(from)) {
                dateExceptions.add(date);
            }
        });
        return Stream.concat(resultSet, dateExceptions.stream());
    }

    private Stream<ZonedDateTime> getSetActiveDaysInCron(ExecutionTime cron, Duration duration) {
        Iterable<ZonedDateTime> iterable = () -> new EventCronIterator(cron, duration, from, to);
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
