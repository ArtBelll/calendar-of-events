package ru.korbit.cecommon.utility;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import lombok.val;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.domain.Showtime;
import ru.korbit.cecommon.domain.SimpleEvent;
import ru.korbit.cecommon.packet.EventCronIterator;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.cronutils.model.CronType.QUARTZ;

public class EventActionHelper {

    private final CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));

    private final Supplier<TreeSet<ZonedDateTime>> supplier =
            () -> new TreeSet<>(DateTimeUtils.zonedDateTimeComparator);


    private final Long cityId;
    private final ZonedDateTime from;
    private final ZonedDateTime to;

    public EventActionHelper(Long cityId, ZonedDateTime from, ZonedDateTime to) {
        this.cityId = cityId;
        this.from = from;
        this.to = to;
    }

    public Set<ZonedDateTime> getSetActives(Event event) {
        if (event instanceof CinemaEvent) {
            return this.getSetActives((CinemaEvent) event);
        } else if (event instanceof SimpleEvent) {
            return getSetActives((SimpleEvent) event);
        } else {
            throw new RuntimeException("Unknown event");
        }
    }

    private Set<ZonedDateTime> getSetActives(SimpleEvent simpleEvent) {
        TreeSet<ZonedDateTime> resultSet = simpleEvent.getActionSchedules()
                .parallelStream()
                .filter(actionSchedule -> actionSchedule.getCity().getId().equals(cityId))
                .map(action -> {
                    val cron = ExecutionTime.forCron(parser.parse(action.getCron()));
                    return getSetActiveDaysInCron(cron, action.getDuration());
                })
                .flatMap(set -> set)
                .filter(date -> !DateTimeUtils.dateInDates(simpleEvent.getNoneAction(), date)
                        && !DateTimeUtils.dateInDates(simpleEvent.getDateExceptions(), date))
                .collect(Collectors.toCollection(supplier));

        simpleEvent.getDateExceptions().forEach(date -> {
            if (date.isBefore(to) && date.isAfter(from)) {
                resultSet.add(date);
            }
        });
        return resultSet;
    }

    private Stream<ZonedDateTime> getSetActiveDaysInCron(ExecutionTime cron, Duration duration) {
        Iterable<ZonedDateTime> iterable = () -> new EventCronIterator(cron, duration, from, to);
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    private Set<ZonedDateTime> getSetActives(CinemaEvent cinemaEvent) {
        return cinemaEvent.getShowtimeList()
                .parallelStream()
                .filter(showtime -> showtime.getHall().getCinema().getCity().getId().equals(cityId)
                        && showtime.getStartTime().isBefore(to) && showtime.getStartTime().isAfter(from))
                .map(Showtime::getStartTime)
                .collect(Collectors.toCollection(supplier));
    }
}
