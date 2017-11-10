package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface EventDao extends GenericDao<Event> {

    Optional<Event> getByTitle(String title);

    Stream<Event> getByDateRangeAtCity(LocalDate startDate,
                                       LocalDate finishDate,
                                       Long cityId,
                                       List<Long> ignoreTypes);

    Stream<Event> searchEvents(String title,
                               String place,
                               LocalDate startDate,
                               Long cityId);
}
