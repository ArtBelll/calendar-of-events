package ru.korbit.cecommon.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

/**
 * Created by Artur Belogur on 08.11.17.
 */
@Service
public class EventStoreHelper extends BaseStoreHelper<Event> implements CommonStoreHelper<Event> {

    private final EventDao eventDao;

    @Autowired
    public EventStoreHelper(EventDao eventDao) {
        super(Event.class);
        this.eventDao = eventDao;
    }

    @Override
    public Optional<Event> searchFromDb(Event event) {
        return eventDao.getByTitle(event.getTitle());
    }
}
