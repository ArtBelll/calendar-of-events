package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.CinemaEventHall;
import ru.korbit.cecommon.domain.Showtime;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@Repository(value = "cinemaEventHallShowtimeDao")
public class ShowtimeDaoImpl extends SessionFactoryHolder<Showtime>
        implements ShowtimeDao {

    ShowtimeDaoImpl() {
        super(Showtime.class);
    }

    @Override
    public void addShowtime(Showtime showtime) {
        super.save(showtime);
    }

    @Override
    public Optional<Showtime> getShowtime(CinemaEventHall id) {
        return super.get(id);
    }

    @Override
    public Optional<Showtime> getShowtimeByRamblerId(Long ramblerId) {
        return getSession()
                .createQuery("SELECT es FROM Showtime es WHERE es.ramblerId = :ramblerId",
                        Showtime.class)
                .setParameter("ramblerId", ramblerId)
                .uniqueResultOptional();
    }
}
