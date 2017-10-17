package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.CinemaEventHallShowtimeDao;
import ru.korbit.cecommon.domain.CinemaEventHallShowtime;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@Repository(value = "cinemaEventHallShowtimeDao")
public class CinemaEventHallShowtimeDaoImpl extends SessionFactoryHolder
        implements CinemaEventHallShowtimeDao {

    @Override
    public long addCinemaEventHallShowtime(CinemaEventHallShowtime cinemaEventHallShowtime) {
        super.save(cinemaEventHallShowtime);
        return cinemaEventHallShowtime.getId();
    }

    @Override
    public Optional<CinemaEventHallShowtime> getCinemaEventHallShowtime(Long id) {
        return getSession().byId(CinemaEventHallShowtime.class).loadOptional(id);
    }

    @Override
    public Optional<CinemaEventHallShowtime> getCinemaEventHallShowtimeByRamblerId(Long id) {
        return getSession()
                .createQuery("SELECT es FROM CinemaEventHallShowtime es WHERE es.id = :id",
                        CinemaEventHallShowtime.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }
}
