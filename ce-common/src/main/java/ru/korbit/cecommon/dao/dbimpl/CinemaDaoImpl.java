package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.domain.Cinema;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Repository(value = "cinemaDao")
public class CinemaDaoImpl extends SessionFactoryHolder implements CinemaDao {

    @Override
    public long addCinema(Cinema cinema) {
        super.save(cinema);
        return cinema.getId();
    }
}
