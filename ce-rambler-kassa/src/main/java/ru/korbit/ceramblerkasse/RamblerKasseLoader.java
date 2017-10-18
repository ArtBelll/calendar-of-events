package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.domain.*;
import ru.korbit.cecommon.services.RamblerKassaService;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerEvent;
import ru.korbit.ceramblerkasse.services.api.impl.RamblerKassa;
import ru.korbit.ceramblerkasse.services.filters.RedisRegion;
import ru.korbit.ceramblerkasse.services.filters.impl.CheckerExistCacheImpl;
import ru.korbit.ceramblerkasse.services.filters.impl.CheckerExistDbImpl;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

import java.util.List;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Slf4j
@Component
public class RamblerKasseLoader implements RamblerKassaService {

    private Integer KALININGRAD_ID = 1700;

    private RamblerKassa ramblerKassa;

    private CheckerExistCacheImpl checkerExistInCache;
    private CheckerExistDbImpl checkerExistInDb;

    @Autowired
    public RamblerKasseLoader(RamblerKassa ramblerKassa,
                              CheckerExistCacheImpl checkerExistInCache,
                              CheckerExistDbImpl checkerExistInDb) {
        this.ramblerKassa = ramblerKassa;
        this.checkerExistInCache = checkerExistInCache;
        this.checkerExistInDb = checkerExistInDb;
    }

    @Override
    @Transactional
    //TODO for all city
    public void load() {
        List<RamblerCity> cities = ramblerKassa.getCities();
        cities.stream()
                .filter(city -> city.getCityRamblerId().equals(KALININGRAD_ID))
                .forEach(ramblerCity -> {

                    val currentCity = getCurrentCity(ramblerCity);

                    checkExistCinemasAtCity(ramblerCity.getCityRamblerId(), currentCity);

                    loadEvents(ramblerCity, currentCity);
                });
    }

    private void checkExistCinemasAtCity(Integer cityRamblerId, City currentCity) {
        ramblerKassa.getCinemasAtCity(cityRamblerId)
                .forEach(ramblerCinema -> {
                    val cinemaDbId = checkerExistInCache
                            .check(ramblerCinema.getCinemaRamblerId(), RedisRegion.CINEMA);
                    if (!cinemaDbId.isPresent()) {
                        val cinemaDb = ramblerCinema.toBDCinema();
                        cinemaDb.setCity(currentCity);
                        checkerExistInDb.checkAndSave(cinemaDb);
                        checkerExistInCache.save(cityRamblerId, cinemaDb.getId(), RedisRegion.CITY);
                    }
                });
    }

    private void checkExistShowtime(Showtime showtime) {
        val showtimeDbId = checkerExistInCache.check(showtime.getRamblerId(), RedisRegion.SHOWTIME);
        if (!showtimeDbId.isPresent()) {
            checkerExistInDb.checkAndSave(showtime);

            val dbId = new CinemaEventHall(showtime.getCinemaEvent().getId(),
                    showtime.getHall().getId(), showtime.getStartTime());
            checkerExistInCache.save(showtime.getRamblerId(), dbId, RedisRegion.SHOWTIME);
        }
    }

    private void loadEvents(RamblerCity ramblerCity, City currentCity) {
        ramblerKassa.getEventsLessDateAtCity(ramblerCity.getCityRamblerId(), TimeUtility.getMaxDate())
                .forEach(ramblerEvent -> {
                    val currentEvent = getCurrentEvent(ramblerEvent);

                    loadShowtimes(ramblerCity, currentEvent, ramblerEvent);
                });
    }

    private void loadShowtimes(RamblerCity ramblerCity, Event currentEvent, RamblerEvent ramblerEvent) {
        ramblerKassa.getShowtimesEventLessDateAtCity(ramblerCity.getCityRamblerId(),
                TimeUtility.getMaxDate(), ramblerEvent.getEventRamblerId())
                .forEach(ramblerShowtime -> {
                    val currentCinema = getCurrentCinema(ramblerShowtime.getPlaceId());
                    val currentHall = getCurrentHall(ramblerShowtime.toDBHall());

                    if (!currentCinema.getHalls().contains(currentHall)) {
                        currentHall.setCinema(currentCinema);
                        checkerExistInDb.updateObject(currentCinema);
                    }

                    val currentShowtime = ramblerShowtime.toDbShowtime();
                    currentShowtime.setCinemaEvent((CinemaEvent) currentEvent);
                    currentShowtime.setHall(currentHall);

                    checkExistShowtime(currentShowtime);
                });
    }

    private City getCurrentCity(RamblerCity ramblerCity) {
        final City currentCity;

        val cityDbId = checkerExistInCache.check(ramblerCity.getCityRamblerId(),
                RedisRegion.CITY);
        if (cityDbId.isPresent()) {
            currentCity = checkerExistInDb.getObject(cityDbId.get(), City.class);
        } else {
            currentCity = checkerExistInDb.checkAndSave(ramblerCity.toDBCity());
            checkerExistInCache.save(ramblerCity.getCityRamblerId(),
                     currentCity.getId(), RedisRegion.CITY);
        }

        return currentCity;
    }

    private Cinema getCurrentCinema(Integer ramblerCinemaId) {
        final Cinema currentCinema;

        val cinemaDbId = checkerExistInCache.check(ramblerCinemaId, RedisRegion.CINEMA);
        if (cinemaDbId.isPresent()) {
            currentCinema = checkerExistInDb.getObject(cinemaDbId.get(), Cinema.class);
        }
        else {
            val ramblerCinema = ramblerKassa.getCinema(ramblerCinemaId);
            currentCinema = checkerExistInDb.checkAndSave(ramblerCinema.toBDCinema());
            checkerExistInCache.save(ramblerCinemaId, currentCinema.getId(), RedisRegion.CINEMA);
        }

        return currentCinema;
    }

    private Event getCurrentEvent(RamblerEvent ramblerEvent) {
        final Event currentEvent;

        val eventDbId = checkerExistInCache
                .check(ramblerEvent.getEventRamblerId(), RedisRegion.EVENT);
        if (eventDbId.isPresent()) {
            currentEvent = checkerExistInDb.getObject(eventDbId.get(), Event.class);
        } else {
            currentEvent = checkerExistInDb.checkAndSave(ramblerEvent.toDBEvent());
            checkerExistInCache.save(ramblerEvent.getEventRamblerId(),
                    currentEvent.getId(), RedisRegion.EVENT);
        }

        return currentEvent;
    }

    private Hall getCurrentHall(Hall hall) {
        final Hall currentHall;

        val hallDbId = checkerExistInCache.check(hall.getRamblerId(), RedisRegion.HALL);
        if (hallDbId.isPresent()) {
            currentHall = checkerExistInDb.getObject(hallDbId.get(), Hall.class);
        } else {
            currentHall = checkerExistInDb.checkAndSave(hall);
            checkerExistInCache.save(hall.getRamblerId(),
                    currentHall.getId(), RedisRegion.HALL);
        }

        return currentHall;
    }
}
