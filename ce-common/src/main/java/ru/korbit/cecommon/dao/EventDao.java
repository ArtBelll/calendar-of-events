package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Event;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface EventDao {

    Optional<Event> getEventById(@NonNull Long id);

    long addEvent(@NonNull Event event);

    Stream<Event> getEventsByDateRange(@NonNull LocalDate startDate, @NonNull LocalDate finishDate);

    Stream<Event> searchEvents(@NonNull String title, @NonNull String place);
}
