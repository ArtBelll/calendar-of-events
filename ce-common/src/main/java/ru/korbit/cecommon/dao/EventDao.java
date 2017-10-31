package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface EventDao {

    Optional<Event> getEventById(@NonNull Long id);

    long addEvent(@NonNull Event event);

    Optional<Event> getEventByTitle(@NonNull String title);

    Stream<Event> getEventsByDateRangeAtCity(@NonNull LocalDate startDate,
                                             @NonNull LocalDate finishDate,
                                             @NonNull Long cityId,
                                             @NonNull List<Long> ignoreTypes);

    Stream<Event> searchEvents(@NonNull String title, @NonNull String place, @NonNull LocalDate startDate);
}
