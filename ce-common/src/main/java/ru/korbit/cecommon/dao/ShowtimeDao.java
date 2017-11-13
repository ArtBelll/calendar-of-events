package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Showtime;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface ShowtimeDao extends GenericDao<Showtime> {

    Stream<Showtime> getByHallOnDay(Long hallId, ZonedDateTime dateFrom);
}
