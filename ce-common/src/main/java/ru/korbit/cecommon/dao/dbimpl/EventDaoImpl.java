package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.utility.StringUtils;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
@Repository(value = "eventDao")
public class EventDaoImpl extends SessionFactoryHolder implements ru.korbit.cecommon.dao.EventDao {

    @Override
    public Optional<Event> getEventById(Long id) {
        return getSession().byId(Event.class).loadOptional(id);
    }

    @Override
    public long addEvent(Event event) {
        super.save(event);
        return event.getId();
    }

    @Override
    public Optional<Event> getEventByTitle(String title) {
        return getSession()
                .createQuery("SELECT e FROM Event e WHERE e.title = :title", Event.class)
                .setParameter("title", title)
                .uniqueResultOptional();
    }

    @Override
    public Stream<Event> getEventsByDateRange(LocalDate startDate, LocalDate finishDate) {
        return getSession()
                .createQuery("SELECT e FROM Event e " +
                        "WHERE e.startDay < :finishDay AND e.finishDay > :startDay", Event.class)
                .setParameter("startDay", startDate)
                .setParameter("finishDay", finishDate)
                .stream();
    }

    @Override
    public Stream<Event> searchEvents(String title, String place) {
        return getSession()
                .createQuery("SELECT e FROM Event e " +
                        "LEFT JOIN SimpleEvent se ON e.id = se.id " +
                        "LEFT JOIN CinemaEvent ce ON e.id = ce.id " +
                        "LEFT JOIN ce.cinemas cin " +
                        "WHERE LOWER(e.title) LIKE :title " +
                            "AND (LOWER(se.place) LIKE :place " +
                                "OR LOWER(cin.place) LIKE :place " +
                                "OR LOWER(cin.name) LIKE :place)", Event.class)
                .setParameter("title", StringUtils.getSqlPatternInAnyPosition(title))
                .setParameter("place", StringUtils.getSqlPatternInAnyPosition(place))
                .stream();
    }
}
