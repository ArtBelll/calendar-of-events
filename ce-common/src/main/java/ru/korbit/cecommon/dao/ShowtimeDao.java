package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Showtime;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface ShowtimeDao extends GenericDao<Showtime> {

    List<Showtime> getByHallAndEventOnDay(Long eventId, Long hallId, ZonedDateTime dateFrom);
}
