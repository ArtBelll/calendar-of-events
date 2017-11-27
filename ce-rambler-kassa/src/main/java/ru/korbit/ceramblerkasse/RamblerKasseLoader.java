package ru.korbit.ceramblerkasse;

import javafx.util.Pair;
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
import ru.korbit.ceramblerkasse.api.RamblerKassaApi;
import ru.korbit.ceramblerkasse.utility.TimeUtility;
import sun.awt.Mutex;

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

    private final AsyncLoader asyncLoader;

    private final TimeZone timeZone;

    private final Mutex timeZoneMutex = new Mutex();

    @Autowired
    public RamblerKasseLoader(RamblerKassaApi ramblerKassa,
                              StoresHelpersHolder storesHelpersHolder,
                              AsyncLoader asyncLoader, TimeZone timeZone) {
        this.ramblerKassa = ramblerKassa;
        this.storesHelpersHolder = storesHelpersHolder;
        this.asyncLoader = asyncLoader;
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
                        timeZoneMutex.lock();
                        unlockTimeZoneMutex();
                        currentCity.setZoneOffset(timeZone
                                .getZoneOffsetByLatlng(ramblerCity.getLat(), ramblerCity.getLng()));
                    }

                    loadCinemas(ramblerCity.getCityRamblerId(), currentCity);
                    loadEvents(ramblerCity.getCityRamblerId(), currentCity);
                    asyncLoader.loadShowtimes(ramblerCity.getCityRamblerId(), currentCity);
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
        ramblerKassa.getEventsLessDateAtCity(cityRamblerId,
                TimeUtility.getMaxDate(currentCity.getZoneOffset()).plusDays(1))
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
                });
    }

    private void unlockTimeZoneMutex() {
        // service allows to make requests only once a second
        val zoneTimeInterval = 1100;
        try {
            Thread.sleep(zoneTimeInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timeZoneMutex.unlock();
    }
}
