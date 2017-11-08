package ru.korbit.cecommon.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.EventTypeDao;
import ru.korbit.cecommon.domain.EventType;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

/**
 * Created by Artur Belogur on 08.11.17.
 */
@Service
public class EventTypeStoreHelper extends BaseStoreHelper<EventType>
        implements CommonStoreHelper<EventType> {

    private final EventTypeDao eventTypeDao;

    @Autowired
    public EventTypeStoreHelper(EventTypeDao eventTypeDao) {
        super(EventType.class);
        this.eventTypeDao = eventTypeDao;
    }

    @Override
    public Optional<EventType> searchFromDb(EventType eventType) {
        return eventTypeDao.getByName(eventType.getName());
    }
}
