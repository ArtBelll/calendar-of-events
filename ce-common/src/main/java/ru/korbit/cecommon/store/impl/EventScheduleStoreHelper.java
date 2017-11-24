package ru.korbit.cecommon.store.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.EventScheduleDao;
import ru.korbit.cecommon.domain.CityEvent;
import ru.korbit.cecommon.domain.EventSchedule;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

@Service
public class EventScheduleStoreHelper extends BaseStoreHelper<EventSchedule>
        implements CommonStoreHelper<EventSchedule> {

    private final EventScheduleDao eventScheduleDao;

    @Autowired
    public EventScheduleStoreHelper(EventScheduleDao eventScheduleDao) {
        super(EventSchedule.class);
        this.eventScheduleDao = eventScheduleDao;
    }

    @Override
    public Optional<EventSchedule> searchFromDb(EventSchedule eventSchedule) {
        val id = new CityEvent(eventSchedule.getEvent().getId(),
                eventSchedule.getCity().getId());
        return eventScheduleDao.get(id);
    }
}
