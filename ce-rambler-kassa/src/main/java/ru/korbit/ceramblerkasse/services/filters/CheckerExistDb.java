package ru.korbit.ceramblerkasse.services.filters;

import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.domain.CinemaEventHallShowtime;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.Hall;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExistDb {

    City checkAndSave(City city);

    Cinema checkAndSave(Cinema cinema);

    Hall checkAndSave(Hall hall);

    CinemaEventHallShowtime checkAndSave(CinemaEventHallShowtime showtime);

    <T> T getObject(Long dbId, Class<T> tClass);
}
