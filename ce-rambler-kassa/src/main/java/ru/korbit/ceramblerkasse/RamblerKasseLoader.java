package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.domain.*;
import ru.korbit.cecommon.services.RamblerKassaService;
import ru.korbit.cecommon.services.TimeZone;
import ru.korbit.cecommon.store.StoresHelpersHolder;
import ru.korbit.cecommon.store.cacheregions.RamblerCacheRegion;
import ru.korbit.cecommon.utility.DateTimeUtils;
import ru.korbit.ceramblerkasse.api.RamblerKassaApi;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

import java.time.ZoneOffset;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Slf4j
@Component
@Transactional
public class RamblerKasseLoader implements RamblerKassaService {

    private Integer KALININGRAD_ID = 1700;
    private Integer MOSCOW_ID = 2;

    private final RamblerKassaApi ramblerKassa;

    private final StoresHelpersHolder storesHelpersHolder;

    private final TimeZone timeZone;

    @Autowired
    public RamblerKasseLoader(RamblerKassaApi ramblerKassa,
                              StoresHelpersHolder storesHelpersHolder,
                              TimeZone timeZone) {
        this.ramblerKassa = ramblerKassa;
        this.storesHelpersHolder = storesHelpersHolder;
        this.timeZone = timeZone;
    }

    @Override
    //TODO for all city
    public void load() {
        ramblerKassa.getCities().stream()
                .filter(city -> city.getCityRamblerId().equals(KALININGRAD_ID)
                        || city.getCityRamblerId().equals(MOSCOW_ID))
                .forEach(ramblerCity -> {
                    val currentCity = storesHelpersHolder.putIfAbsent(
                            ramblerCity.getCityRamblerId(),
                            ramblerCity.toDBCity(),
                            RamblerCacheRegion.CITY);

                    if (currentCity.getZoneOffset() == null) {
                        currentCity.setZoneOffset(timeZone
                                .getZoneOffsetByLatlng(ramblerCity.getLat(), ramblerCity.getLng()));
                    }

                    loadCinemas(ramblerCity.getCityRamblerId(), currentCity);
                    loadEvents(ramblerCity.getCityRamblerId(), currentCity);
                    loadShowtimes(ramblerCity.getCityRamblerId(), currentCity.getZoneOffset());
                });
    }

    private void loadCinemas(Integer cityRamblerId, City currentCity) {
        ramblerKassa.getCinemasAtCity(cityRamblerId)
                .forEach(ramblerCinema -> {
                    val currentCinema = storesHelpersHolder.putIfAbsent(
                            ramblerCinema.getCinemaRamblerId(),
                            ramblerCinema.toBDCinema(),
                            RamblerCacheRegion.CINEMA);
                    currentCinema.setCity(currentCity);
                });
    }

    private void loadEvents(Integer cityRamblerId, City currentCity) {
        ramblerKassa.getEventsLessDateAtCity(cityRamblerId, TimeUtility.getMaxDate(currentCity.getZoneOffset()))
                .forEach(ramblerEvent -> {
                    val eventType = storesHelpersHolder.putIfAbsent(
                            ramblerEvent.getType(),
                            new EventType(ramblerEvent.getType()),
                            RamblerCacheRegion.EVENT_TYPE
                    );

                    val currentEvent = storesHelpersHolder.putIfAbsent(
                            ramblerEvent.getEventRamblerId(),
                            ramblerEvent.toDBEvent(),
                            RamblerCacheRegion.EVENT
                    );

                    if (!currentEvent.getEventTypes().contains(eventType)) {
                        eventType.getEvents().add(currentEvent);
                        currentEvent.getEventTypes().add(eventType);
                        storesHelpersHolder.updateDb(currentEvent);
                    }
                    if (!currentEvent.getCities().contains(currentCity)) {
                        currentCity.getEvents().add(currentEvent);
                        currentEvent.getCities().add(currentCity);
                        storesHelpersHolder.updateDb(currentEvent);
                    }
                });
    }

    private void loadShowtimes(Integer cityRamblerId, ZoneOffset offset) {
        ramblerKassa.getShowtimesCityLessDate(cityRamblerId, TimeUtility.getMaxDate(offset))
                .forEach(ramblerShowtime -> {

                    val currentEvent = storesHelpersHolder.getUsingCache(
                            ramblerShowtime.getEventId(),
                            RamblerCacheRegion.EVENT,
                            Event.class
                    );
                    val currentCinema = storesHelpersHolder.getUsingCache(
                            ramblerShowtime.getPlaceId(),
                            RamblerCacheRegion.CINEMA,
                            Cinema.class
                    );
                    val currentHall = storesHelpersHolder.putIfAbsent(
                            ramblerShowtime.getHallRamblerId(),
                            ramblerShowtime.toDBHall(),
                            RamblerCacheRegion.HALL
                    );

                    if (!currentCinema.getHalls().contains(currentHall)) {
                        currentHall.setCinema(currentCinema);
                    }

                    val showtimeDb = ramblerShowtime.toDbShowtime(offset);
                    showtimeDb.setCinemaEvent((CinemaEvent) currentEvent);
                    showtimeDb.setHall(currentHall);
                    val currentShowtime = storesHelpersHolder.putIfAbsent(
                            ramblerShowtime.getShowtimeRamblerId(),
                            showtimeDb,
                            RamblerCacheRegion.SHOWTIME,
                            DateTimeUtils.getExpireMillis(showtimeDb.getStartTime())
                    );

                    if (currentShowtime.getStartTime().isBefore(currentEvent.getStartDay())) {
                        currentEvent.setStartDay(currentShowtime.getStartTime());
                    }
                    if (currentShowtime.getStartTime().isAfter(currentEvent.getFinishDay())) {
                        currentEvent.setFinishDay(currentShowtime.getStartTime());
                        storesHelpersHolder.updateExpire(
                                ramblerShowtime.getEventId(),
                                currentEvent.getId(),
                                RamblerCacheRegion.EVENT,
                                DateTimeUtils.getExpireMillis(currentEvent.getFinishDay().plusDays(1)),
                                Event.class);
                    }
                });
    }
}
