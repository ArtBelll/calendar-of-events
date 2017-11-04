package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.EventTypeDao;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 19.10.17.
 */
@Repository(value = "eventTypeDao")
public class EventTypeDaoImpl extends SessionFactoryHolder<EventType> implements EventTypeDao {

    EventTypeDaoImpl() {
        super(EventType.class);
    }

    @Override
    public Optional<EventType> getByName(String name) {
        return getSession()
                .createQuery("SELECT et FROM EventType et WHERE et.name = :name", EventType.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public Stream<EventType> getAtCity(Long id) {
        return getSession()
                .createQuery("SELECT DISTINCT et FROM City c " +
                        "JOIN c.events e " +
                        "JOIN e.eventTypes et " +
                        "WHERE c.id = :cityId", EventType.class)
                .setParameter("cityId", id)
                .stream();
    }
}
