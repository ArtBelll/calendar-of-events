package ru.korbit.ceramblerkasse.services.filters.impl;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.dao.CinemaEventHallShowtimeDao;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.dao.HallDao;
import ru.korbit.cecommon.domain.Hall;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;
import ru.korbit.ceramblerkasse.services.filters.CheckerExist;


/**
 * Created by Artur Belogur on 16.10.17.
 */
@Component
@Slf4j
public class CheckerExistInDb implements CheckerExist {

    private final CityDao cityDao;
    private final CinemaDao cinemaDao;
    private final HallDao hallDao;
    private final CinemaEventHallShowtimeDao cinemaEventHallShowtimeDao;

    @Autowired
    public CheckerExistInDb(CityDao cityDao, CinemaDao cinemaDao, HallDao hallDao,
                            CinemaEventHallShowtimeDao cinemaEventHallShowtimeDao) {
        this.cityDao = cityDao;
        this.cinemaDao = cinemaDao;
        this.hallDao = hallDao;
        this.cinemaEventHallShowtimeDao = cinemaEventHallShowtimeDao;
    }

    @Override
    public Pair<RamblerCity, Boolean> checkAndSave(RamblerCity city) {
        val isExist = cityDao.getCityByName(city.getName()).isPresent();
        if(!isExist) {
            cityDao.addCity(city.toDBCity());
            log.debug("Add entity = {}", city);
        }

        return new Pair<>(city, isExist);
    }

    @Override
    public Pair<RamblerCinema, Boolean> checkAndSave(RamblerCinema cinema) {
        val isExist = cinemaDao.getCinemaByName(cinema.getName()).isPresent();
        if(!isExist) {
            cinemaDao.addCinema(cinema.toBDCinema());
            log.debug("Add entity to DB = {}", cinema);
        }

        return new Pair<>(cinema, isExist);
    }

    @Override
    public Pair<Hall, Boolean> checkAndSave(Hall hall) {
        val isExist = hallDao.getHallByRamblerId(hall.getRamblerId()).isPresent();
        if(!isExist) {
            hallDao.addHall(hall);
            log.debug("Add entity to DB = {}", hall);
        }

        return new Pair<>(hall, isExist);
    }

    @Override
    public Pair<RamblerShowtime, Boolean> checkAndSave(RamblerShowtime showtime) {
        val isExist = cinemaEventHallShowtimeDao
                .getCinemaEventHallShowtimeByRamblerId(showtime.getHallRamblerId()).isPresent();
        if(!isExist) {
            cinemaEventHallShowtimeDao.addCinemaEventHallShowtime(showtime.toCinemaEventHallShowtime());
            log.debug("Add entity to DB = {}", showtime);
        }

        return new Pair<>(showtime, isExist);
    }
}
