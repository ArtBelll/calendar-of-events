package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Cinema;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public interface CinemaDao extends GenericDao<Cinema> {

    Cinema save(Cinema cinema);

    Optional<Cinema> getByName(String name);

    Stream<Cinema> getByEventOnDay(Long cityId, Long eventId, ZonedDateTime dateFrom);
}
