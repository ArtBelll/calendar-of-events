package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.domain.Cinema;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Repository(value = "cinemaDao")
public class CinemaDaoImpl extends SessionFactoryHolder<Cinema> implements CinemaDao {

    CinemaDaoImpl() {
        super(Cinema.class);
    }

    @Override
    public Optional<Cinema> getByName(String name) {
        return getSession()
                .createQuery("SELECT c FROM Cinema c WHERE c.name = :name", Cinema.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public Stream<Cinema> getByEventOnDay(Long eventId, LocalDateTime dateFrom) {
        return getSession()
                .createQuery("SELECT DISTINCT c FROM Cinema c " +
                        "JOIN c.halls h " +
                        "JOIN h.showtimeList sh ON sh.startTime > :dateFrom AND sh.startTime < :dateTo " +
                        "JOIN sh.cinemaEvent e " +
                        "WHERE e.id = :eventId", Cinema.class)
                .setParameter("eventId", eventId)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateFrom.toLocalDate().plusDays(1).atStartOfDay())
                .stream();
    }
}
