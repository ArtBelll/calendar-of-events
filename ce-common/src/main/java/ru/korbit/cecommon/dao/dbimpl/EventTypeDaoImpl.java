package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.EventTypeDao;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;

/**
 * Created by Artur Belogur on 19.10.17.
 */
@Repository(value = "eventTypeDao")
public class EventTypeDaoImpl extends SessionFactoryHolder<EventType> implements EventTypeDao {

    EventTypeDaoImpl() {
        super(EventType.class);
    }

    @Override
    public long addEventType(EventType eventType) {
        super.save(eventType);
        return eventType.getId();
    }

    @Override
    public Optional<EventType> getEventTypeByName(String name) {
        return getSession()
                .createQuery("SELECT et FROM EventType et WHERE et.name = :name", EventType.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }
}
