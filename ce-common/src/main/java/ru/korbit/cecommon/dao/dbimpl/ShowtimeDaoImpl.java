package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.Showtime;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@Repository(value = "cinemaEventHallShowtimeDao")
public class ShowtimeDaoImpl extends SessionFactoryHolder<Showtime>
        implements ShowtimeDao {

    public ShowtimeDaoImpl() {
        super(Showtime.class);
    }

    @Override
    public Stream<Showtime> getByHallOnDay(Long hallId, ZonedDateTime dateFrom) {
        return getSession()
                .createQuery("SELECT DISTINCT sh FROM Hall h " +
                        "JOIN h.showtimeList sh " +
                        "WHERE h.id = :hallId " +
                            "AND sh.startTime > :dateFrom " +
                            "AND sh.startTime < :dateTo ", Showtime.class)
                .setParameter("hallId", hallId)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateFrom.plusDays(1))
                .stream();
    }
}
