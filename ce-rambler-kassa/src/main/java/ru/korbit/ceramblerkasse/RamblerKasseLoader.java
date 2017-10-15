package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.services.RamblerKassaService;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Slf4j
@Component
public class RamblerKasseLoader implements RamblerKassaService {

    private final CinemaDao cinemaDao;

    @Autowired
    public RamblerKasseLoader(CinemaDao cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    @Override
    @Transactional
    public void load() {
        val cinema = new Cinema();
        cinema.setCinemaName("Test Cinema");

        val id = cinemaDao.addCinema(cinema);
        log.debug("id = {}", id);

        cinemaDao.delete(cinema);
    }
}
