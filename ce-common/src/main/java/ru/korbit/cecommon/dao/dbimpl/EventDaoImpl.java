package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.utility.StringUtils;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
@Repository(value = "eventDao")
public class EventDaoImpl extends SessionFactoryHolder<Event> implements ru.korbit.cecommon.dao.EventDao {

    EventDaoImpl() {
        super(Event.class);
    }

    @Override
    public Optional<Event> getByTitle(String title) {
        return getSession()
                .createQuery("SELECT e FROM Event e WHERE e.title = :title", Event.class)
                .setParameter("title", title)
                .uniqueResultOptional();
    }

    @Override
    public Stream<Event> getByDateRangeAtCity(ZonedDateTime startDate, ZonedDateTime finishDate,
                                              Long cityId, List<Long> ignoreTypes) {
        ignoreTypes.add(-1L);
        return getSession()
                .createQuery("SELECT e FROM Event e " +
                        "JOIN e.eventTypes et ON et.id NOT IN (:ignoreTypes)" +
                        "JOIN e.eventSchedules es ON es.city.id = :cityId " +
                        "WHERE es.start <= :finishDay AND es.finish >= :startDay", Event.class)
                .setParameter("cityId", cityId)
                .setParameter("ignoreTypes", ignoreTypes)
                .setParameter("startDay", startDate)
                .setParameter("finishDay", finishDate)
                .stream();
    }

    @Override
    public Stream<Event> searchEvents(String title, String place, ZonedDateTime startDate, Long cityId) {
        return getSession()
                .createQuery("SELECT DISTINCT e FROM Event e " +
                        "LEFT JOIN RecurringEvent se ON e.id = se.id " +
                        "LEFT JOIN CinemaEvent ce ON e.id = ce.id " +
                        "JOIN e.cities c ON c.id = :cityId " +
                        "LEFT JOIN c.cinemas cin " +
                        "WHERE e.finishDay >= :startDate " +
                            "AND LOWER(e.title) LIKE :title " +
                            "AND (LOWER(cin.name) LIKE :place " +
                                "OR LOWER(se.place) LIKE :place)" , Event.class)
                .setParameter("cityId", cityId)
                .setParameter("title", StringUtils.getSqlPatternInAnyPosition(title))
                .setParameter("place", StringUtils.getSqlPatternInAnyPosition(place))
                .setParameter("startDate", startDate)
                .stream();
    }
}
