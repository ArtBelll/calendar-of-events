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
import ru.korbit.ceramblerkasse.services.store.impl.RedisRegion;
import ru.korbit.ceramblerkasse.services.store.impl.CacheStoreImpl;
import ru.korbit.ceramblerkasse.services.store.impl.DatabaseStoreImpl;
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

    private CacheStoreImpl cacheStore;
    private DatabaseStoreImpl databaseStore;

    @Autowired
    public RamblerKasseLoader(RamblerKassa ramblerKassa,
                              CacheStoreImpl cacheStore,
                              DatabaseStoreImpl databaseStore) {
        this.ramblerKassa = ramblerKassa;
        this.cacheStore = cacheStore;
        this.databaseStore = databaseStore;
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

                    loadShowtimes(ramblerCity);
                });
    }

    private void checkExistCinemasAtCity(Integer cityRamblerId, City currentCity) {
        ramblerKassa.getCinemasAtCity(cityRamblerId)
                .forEach(ramblerCinema -> {
                    val cinemaDbId = cacheStore
                            .check(ramblerCinema.getCinemaRamblerId(), RedisRegion.CINEMA);
                    if (!cinemaDbId.isPresent()) {
                        val cinemaDb = ramblerCinema.toBDCinema();
                        cinemaDb.setCity(currentCity);
                        val currentCinema = databaseStore.getOrSave(cinemaDb);
                        cacheStore.save(ramblerCinema.getCinemaRamblerId(), currentCinema.getId(),
                                RedisRegion.CINEMA);
                    }
                });
    }

    private void checkExistShowtime(Showtime showtime) {
        val showtimeDbId = cacheStore.check(showtime.getRamblerId(), RedisRegion.SHOWTIME);
        if (!showtimeDbId.isPresent()) {
            databaseStore.getOrSave(showtime);

            val dbId = new CinemaEventHall(showtime.getCinemaEvent().getId(),
                    showtime.getHall().getId(), showtime.getStartTime());
            cacheStore.save(showtime.getRamblerId(), dbId, RedisRegion.SHOWTIME);
        }
    }

    private void loadEvents(RamblerCity ramblerCity, City currentCity) {
        ramblerKassa.getEventsLessDateAtCity(ramblerCity.getCityRamblerId(), TimeUtility.getMaxDate())
                .forEach(ramblerEvent -> {
                    ramblerEvent.setCity(currentCity);
                    checkExistEvent(ramblerEvent);
                });
    }

    private void loadShowtimes(RamblerCity ramblerCity) {
        ramblerKassa.getShowtimesCityLessDate(ramblerCity.getCityRamblerId(), TimeUtility.getMaxDate())
                .forEach(ramblerShowtime -> {

                    val currentEvent = getCurrentEvent(ramblerShowtime.getEventId());
                    val currentCinema = getCurrentCinema(ramblerShowtime.getPlaceId());
                    val currentHall = getCurrentHall(ramblerShowtime.toDBHall());

                    if (!currentCinema.getHalls().contains(currentHall)) {
                        currentHall.setCinema(currentCinema);
                        databaseStore.update(currentCinema);
                    }

                    val currentShowtime = ramblerShowtime.toDbShowtime();
                    currentShowtime.setCinemaEvent((CinemaEvent) currentEvent);
                    currentShowtime.setHall(currentHall);

                    checkExistShowtime(currentShowtime);

                    boolean isUpdateDate = false;
                    if (currentShowtime.getStartTime().toLocalDate().isBefore(currentEvent.getStartDay())) {
                        currentEvent.setStartDay(currentShowtime.getStartTime().toLocalDate());
                        isUpdateDate = true;
                    }
                    if (currentShowtime.getStartTime().toLocalDate().isAfter(currentEvent.getFinishDay())) {
                        currentEvent.setFinishDay(currentShowtime.getStartTime().toLocalDate());
                        isUpdateDate = true;
                    }

                    if (isUpdateDate) databaseStore.update(currentEvent);
                });
    }

    private City getCurrentCity(RamblerCity ramblerCity) {
        final City currentCity;

        val cityDbId = cacheStore.check(ramblerCity.getCityRamblerId(), RedisRegion.CITY);
        if (cityDbId.isPresent()) {
            currentCity = databaseStore.get(cityDbId.get(), City.class);
        } else {
            currentCity = databaseStore.getOrSave(ramblerCity.toDBCity());
            cacheStore.save(ramblerCity.getCityRamblerId(),
                    currentCity.getId(), RedisRegion.CITY);
        }

        return currentCity;
    }

    private Cinema getCurrentCinema(Integer ramblerCinemaId) {
        final Cinema currentCinema;

        val cinemaDbId = cacheStore.check(ramblerCinemaId, RedisRegion.CINEMA);
        if (cinemaDbId.isPresent()) {
            currentCinema = databaseStore.get(cinemaDbId.get(), Cinema.class);
        } else {
            val ramblerCinema = ramblerKassa.getCinema(ramblerCinemaId);
            currentCinema = databaseStore.getOrSave(ramblerCinema.toBDCinema());
            cacheStore.save(ramblerCinemaId, currentCinema.getId(), RedisRegion.CINEMA);
        }

        return currentCinema;
    }

    private Event checkExistEvent(RamblerEvent ramblerEvent) {
        final Event currentEvent;

        val eventDbId = cacheStore
                .check(ramblerEvent.getEventRamblerId(), RedisRegion.EVENT);
        if (eventDbId.isPresent()) {
            currentEvent = databaseStore.get(eventDbId.get(), Event.class);
        } else {
            val currentEventType = getCurrentEventType(ramblerEvent.toDBEventType());
            val eventDb = ramblerEvent.toDBEvent();
            eventDb.getEventTypes().add(currentEventType);

            currentEvent = databaseStore.getOrSave(eventDb);
            cacheStore.save(ramblerEvent.getEventRamblerId(),
                    currentEvent.getId(), RedisRegion.EVENT);

            currentEventType.getEvents().add(eventDb);
            databaseStore.update(currentEventType);
        }

        return currentEvent;
    }

    private Event getCurrentEvent(Integer ramblerEventId) {
        return cacheStore.check(ramblerEventId, RedisRegion.EVENT)
                .map(eventDbId -> databaseStore.get(eventDbId, Event.class))
                .orElse(null);
    }

    private Hall getCurrentHall(Hall hall) {
        final Hall currentHall;

        val hallDbId = cacheStore.check(hall.getRamblerId(), RedisRegion.HALL);
        if (hallDbId.isPresent()) {
            currentHall = databaseStore.get(hallDbId.get(), Hall.class);
        } else {
            currentHall = databaseStore.getOrSave(hall);
            cacheStore.save(hall.getRamblerId(),
                    currentHall.getId(), RedisRegion.HALL);
        }

        return currentHall;
    }

    private EventType getCurrentEventType(EventType eventType) {
        final EventType currentEventType;

        val eventTypeDbId = cacheStore.check(eventType.getName(), RedisRegion.EVENT_TYPE);
        if (eventTypeDbId.isPresent()) {
            currentEventType = databaseStore.get(eventTypeDbId.get(), EventType.class);
        } else {
            currentEventType = databaseStore.getOrSave(eventType);
            cacheStore.save(eventType.getName(), currentEventType.getId(), RedisRegion.EVENT_TYPE);
        }

        return currentEventType;
    }
}
