package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.domain.*;
import ru.korbit.cecommon.services.RamblerKassaService;
import ru.korbit.cecommon.store.StoresHelpersHolder;
import ru.korbit.cecommon.store.cacheregions.RamblerCacheRegion;
import ru.korbit.cecommon.utility.DateTimeUtils;
import ru.korbit.ceramblerkasse.api.RamblerKassaApi;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

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

    @Autowired
    public RamblerKasseLoader(RamblerKassaApi ramblerKassa,
                              StoresHelpersHolder storesHelpersHolder) {
        this.ramblerKassa = ramblerKassa;
        this.storesHelpersHolder = storesHelpersHolder;
    }

    @Override
    //TODO for all city
    public void load() {
        ramblerKassa.getCities().stream()
                .filter(city -> city.getCityRamblerId().equals(KALININGRAD_ID))
                .forEach(ramblerCity -> {
                    val currentCity = storesHelpersHolder.putIfAbsent(
                            ramblerCity.getCityRamblerId(),
                            ramblerCity.toDBCity(),
                            RamblerCacheRegion.CITY);

                    loadCinemas(ramblerCity.getCityRamblerId(), currentCity);
                    loadEvents(ramblerCity.getCityRamblerId(), currentCity);
                    loadShowtimes(ramblerCity.getCityRamblerId());
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
        ramblerKassa.getEventsLessDateAtCity(cityRamblerId, TimeUtility.getMaxDate())
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
                    currentEvent.setCity(currentCity);
                });
    }

    private void loadShowtimes(Integer cityRamblerId) {
        ramblerKassa.getShowtimesCityLessDate(cityRamblerId, TimeUtility.getMaxDate())
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

                    val showtimeDb = ramblerShowtime.toDbShowtime();
                    showtimeDb.setCinemaEvent((CinemaEvent) currentEvent);
                    showtimeDb.setHall(currentHall);
                    val currentShowtime = storesHelpersHolder.putIfAbsent(
                            ramblerShowtime.getShowtimeRamblerId(),
                            showtimeDb,
                            RamblerCacheRegion.SHOWTIME,
                            DateTimeUtils.getExpireMillis(showtimeDb.getStartTime())
                    );

                    if (currentShowtime.getStartTime().toLocalDate().isBefore(currentEvent.getStartDay())) {
                        currentEvent.setStartDay(currentShowtime.getStartTime().toLocalDate());
                    }
                    if (currentShowtime.getStartTime().toLocalDate().isAfter(currentEvent.getFinishDay())) {
                        currentEvent.setFinishDay(currentShowtime.getStartTime().toLocalDate());
                        storesHelpersHolder.updateExpire(
                                ramblerShowtime.getEventId(),
                                currentEvent.getId(),
                                RamblerCacheRegion.EVENT,
                                DateTimeUtils.getExpireMillis(currentEvent.getFinishDay().plusDays(1).atStartOfDay()),
                                Event.class);
                    }
                });
    }
}
