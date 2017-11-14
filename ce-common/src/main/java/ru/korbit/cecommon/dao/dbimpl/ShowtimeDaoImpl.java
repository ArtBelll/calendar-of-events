package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.Showtime;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    public List<Showtime> getByHallAndEventOnDay(Long eventId, Long hallId, ZonedDateTime dateFrom) {
        return getSession()
                .createQuery("SELECT DISTINCT sh FROM Showtime sh " +
                        "WHERE sh.hall.id = :hallId " +
                            "AND sh.cinemaEvent.id = :eventId " +
                            "AND sh.startTime > :dateFrom " +
                            "AND sh.startTime < :dateTo " +
                        "ORDER BY sh.startTime", Showtime.class)
                .setParameter("hallId", hallId)
                .setParameter("eventId", eventId)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateFrom.plusDays(1).truncatedTo(ChronoUnit.DAYS))
                .getResultList();
    }
}
