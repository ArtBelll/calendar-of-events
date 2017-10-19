package ru.korbit.ceramblerkasse.services.filters;

import ru.korbit.cecommon.domain.*;

import java.io.Serializable;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExistDb {

    City checkAndSave(City city);

    Cinema checkAndSave(Cinema cinema);

    Hall checkAndSave(Hall hall);

    Showtime checkAndSave(Showtime showtime);

    Event checkAndSave(Event event);

    EventType checkAndSave(EventType event);

    <T> T getObject(Serializable dbId, Class<T> tClass);

    void updateObject(Object object);
}
