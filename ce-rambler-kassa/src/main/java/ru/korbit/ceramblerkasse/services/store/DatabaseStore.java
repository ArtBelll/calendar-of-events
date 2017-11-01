package ru.korbit.ceramblerkasse.services.store;

import ru.korbit.cecommon.domain.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface DatabaseStore {

    City getOrSave(City city);

    Cinema getOrSave(Cinema cinema);

    Hall getOrSave(Hall hall);

    Showtime getOrSave(Showtime showtime);

    Event getOrSave(Event event);

    EventType getOrSave(EventType event);

    <T> T get(Serializable id, Class<T> tClass);

    void update(Object object);
}
