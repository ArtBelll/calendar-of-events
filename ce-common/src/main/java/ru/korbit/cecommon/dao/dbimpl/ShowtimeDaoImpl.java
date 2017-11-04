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

    public ShowtimeDaoImpl() {
        super(Showtime.class);
    }
}
