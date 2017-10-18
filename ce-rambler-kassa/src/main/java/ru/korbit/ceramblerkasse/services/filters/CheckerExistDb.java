package ru.korbit.ceramblerkasse.services.filters;

import ru.korbit.cecommon.domain.*;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExistDb {

    City checkAndSave(City city);

    Cinema checkAndSave(Cinema cinema);

    Hall checkAndSave(Hall hall);

    CinemaEventHallShowtime checkAndSave(CinemaEventHallShowtime showtime);

    Event checkAndSave(Event event);

    <T> T getObject(Long dbId, Class<T> tClass);
}
