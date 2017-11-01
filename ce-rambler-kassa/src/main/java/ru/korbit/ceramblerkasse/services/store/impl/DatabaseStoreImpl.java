package ru.korbit.ceramblerkasse.services.store.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.*;
import ru.korbit.cecommon.dao.dbimpl.SessionFactoryHolder;
import ru.korbit.cecommon.domain.*;
import ru.korbit.ceramblerkasse.services.store.DatabaseStore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


/**
 * Created by Artur Belogur on 16.10.17.
 */
@Component
@Slf4j
public class DatabaseStoreImpl extends SessionFactoryHolder<Object> implements DatabaseStore {

    private final CityDao cityDao;
    private final CinemaDao cinemaDao;
    private final HallDao hallDao;
    private final ShowtimeDao showtimeDao;
    private final EventDao eventDao;
    private final EventTypeDao eventTypeDao;

    @Autowired
    public DatabaseStoreImpl(CityDao cityDao, CinemaDao cinemaDao, HallDao hallDao,
                             ShowtimeDao showtimeDao, EventDao eventDao, EventTypeDao eventTypeDao) {
        this.cityDao = cityDao;
        this.cinemaDao = cinemaDao;
        this.hallDao = hallDao;
        this.showtimeDao = showtimeDao;
        this.eventDao = eventDao;
        this.eventTypeDao = eventTypeDao;
    }

    @Override
    public City getOrSave(City city) {
        return cityDao.getCityByName(city.getName())
                .orElseGet(() -> save(city, City.class));
    }

    @Override
    public Cinema getOrSave(Cinema cinema) {
        return cinemaDao.getCinemaByName(cinema.getName())
                .orElseGet(() -> save(cinema, Cinema.class));
    }

    @Override
    public Hall getOrSave(Hall hall) {
        return hallDao.getHallByRamblerId(hall.getRamblerId())
                .orElseGet(() -> save(hall, Hall.class));
    }

    @Override
    public Showtime getOrSave(Showtime showtime) {
        val id = new CinemaEventHall(showtime.getCinemaEvent().getId(),
                showtime.getHall().getId(), showtime.getStartTime());
        return showtimeDao.getShowtime(id)
                .orElseGet(() -> save(showtime, Showtime.class));
    }

    @Override
    public Event getOrSave(Event event) {
        return eventDao.getEventByTitle(event.getTitle())
                .orElseGet(() -> save(event, Event.class));
    }

    @Override
    public EventType getOrSave(EventType eventType) {
        return eventTypeDao.getEventTypeByName(eventType.getName())
                .orElseGet(() -> save(eventType, EventType.class));
    }

    @Override
    public <T> T get(Serializable id, Class<T> tClass) {
        return getSession().byId(tClass).load(id);
    }

    @Override
    public void update(Object object) {
        super.update(object);
    }

    private <T> T save(T obj, Class<T> tClass) {
        super.save(obj);
        log.info("Add entity = {}", obj);
        return obj;
    }
}
