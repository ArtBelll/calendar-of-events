package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Event;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface EventDao extends GenericDao<Event> {

    Optional<Event> getByTitle(String title);

    Stream<Event> getByDateRangeAtCity(ZonedDateTime startDate,
                                       ZonedDateTime finishDate,
                                       Long cityId,
                                       List<Long> ignoreTypes);

    Stream<Event> searchEvents(String title,
                               String place,
                               ZonedDateTime startDate,
                               Long cityId);
}
