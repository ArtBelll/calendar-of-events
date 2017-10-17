package ru.korbit.ceramblerkasse.services.filters.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.dao.CinemaEventHallShowtimeDao;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.dao.HallDao;
import ru.korbit.cecommon.dao.dbimpl.SessionFactoryHolder;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.domain.CinemaEventHallShowtime;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.Hall;
import ru.korbit.ceramblerkasse.services.filters.CheckerExistDb;

import java.util.Optional;


/**
 * Created by Artur Belogur on 16.10.17.
 */
@Component
@Slf4j
public class CheckerExistDbImpl extends SessionFactoryHolder implements CheckerExistDb {

    private final CityDao cityDao;
    private final CinemaDao cinemaDao;
    private final HallDao hallDao;
    private final CinemaEventHallShowtimeDao cinemaEventHallShowtimeDao;

    @Autowired
    public CheckerExistDbImpl(CityDao cityDao, CinemaDao cinemaDao, HallDao hallDao,
                              CinemaEventHallShowtimeDao cinemaEventHallShowtimeDao) {
        this.cityDao = cityDao;
        this.cinemaDao = cinemaDao;
        this.hallDao = hallDao;
        this.cinemaEventHallShowtimeDao = cinemaEventHallShowtimeDao;
    }

    @Override
    public City checkAndSave(City city) {
        val searchCity = cityDao.getCityByName(city.getName());
        if(!searchCity.isPresent()) {
            cityDao.addCity(city);
            log.debug("Add entity = {}", city);

            return city;
        }
        else {
            return searchCity.get();
        }
    }

    @Override
    public Cinema checkAndSave(Cinema cinema) {
        val searchCinema = cinemaDao.getCinemaByName(cinema.getName());
        if(!searchCinema.isPresent()) {
            cinemaDao.addCinema(cinema);
            log.debug("Add entity to DB = {}", cinema);

            return cinema;
        }
        else {
            return searchCinema.get();
        }
    }

    @Override
    public Hall checkAndSave(Hall hall) {
        val searchHall = hallDao.getHallByRamblerId(hall.getRamblerId());
        if(!searchHall.isPresent()) {
            hallDao.addHall(hall);
            log.debug("Add entity to DB = {}", hall);

            return hall;
        }
        else {
            return searchHall.get();
        }
    }

    @Override
    public CinemaEventHallShowtime checkAndSave(CinemaEventHallShowtime showtime) {
        val searchShowtime = cinemaEventHallShowtimeDao
                .getCinemaEventHallShowtimeByRamblerId(showtime.getRamblerID());

        if(!searchShowtime.isPresent()) {
            cinemaEventHallShowtimeDao.addCinemaEventHallShowtime(showtime);
            log.debug("Add entity to DB = {}", showtime);

            return showtime;
        }
        else {
            return searchShowtime.get();
        }
    }

    @Override
    public <T> T getObject(Long dbId, Class<T> tClass) {
        return getSession().get(tClass, dbId);
    }
}
