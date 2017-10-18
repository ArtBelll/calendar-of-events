package ru.korbit.ceramblerkasse.services.filters.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.*;
import ru.korbit.cecommon.dao.dbimpl.SessionFactoryHolder;
import ru.korbit.cecommon.domain.*;
import ru.korbit.ceramblerkasse.services.filters.CheckerExistDb;


/**
 * Created by Artur Belogur on 16.10.17.
 */
@Component
@Slf4j
public class CheckerExistDbImpl extends SessionFactoryHolder implements CheckerExistDb {

    private final CityDao cityDao;
    private final CinemaDao cinemaDao;
    private final HallDao hallDao;
    private final ShowtimeDao showtimeDao;
    private final EventDao eventDao;

    @Autowired
    public CheckerExistDbImpl(CityDao cityDao, CinemaDao cinemaDao, HallDao hallDao,
                              ShowtimeDao showtimeDao, EventDao eventDao) {
        this.cityDao = cityDao;
        this.cinemaDao = cinemaDao;
        this.hallDao = hallDao;
        this.showtimeDao = showtimeDao;
        this.eventDao = eventDao;
    }

    @Override
    public City checkAndSave(City city) {
        val searchCity = cityDao.getCityByName(city.getName());
        if (!searchCity.isPresent()) {
            cityDao.addCity(city);
            log.debug("Add entity = {}", city);

            return city;
        } else {
            return searchCity.get();
        }
    }

    @Override
    public Cinema checkAndSave(Cinema cinema) {
        val searchCinema = cinemaDao.getCinemaByName(cinema.getName());
        if (!searchCinema.isPresent()) {
            cinemaDao.addCinema(cinema);
            log.debug("Add entity to DB = {}", cinema);

            return cinema;
        } else {
            return searchCinema.get();
        }
    }

    @Override
    public Hall checkAndSave(Hall hall) {
        val searchHall = hallDao.getHallByRamblerId(hall.getRamblerId());
        if (!searchHall.isPresent()) {
            hallDao.addHall(hall);
            log.debug("Add entity to DB = {}", hall);

            return hall;
        } else {
            return searchHall.get();
        }
    }

    @Override
    public Showtime checkAndSave(Showtime showtime) {
        val id = new CinemaEventHall(showtime.getCinemaEvent().getId(),
                showtime.getHall().getId(), showtime.getStartTime());
        val searchShowtime = showtimeDao.getShowtime(id);

        if (!searchShowtime.isPresent()) {
            showtimeDao.addShowtime(showtime);
            log.debug("Add entity to DB = {}", showtime);

            return showtime;
        } else {
            return searchShowtime.get();
        }
    }

    @Override
    public Event checkAndSave(Event event) {
        val searchEvent = eventDao.getEventByTitle(event.getTitle());

        if (!searchEvent.isPresent()) {
            eventDao.addEvent(event);
            log.debug("Add entity to DB = {}", event);

            return event;
        } else {
            return searchEvent.get();
        }
    }

    @Override
    public <T> T getObject(Long dbId, Class<T> tClass) {
        return getSession().get(tClass, dbId);
    }

    @Override
    public void updateObject(Object object) {
        getSession().update(object);
    }
}
