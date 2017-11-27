package ru.korbit.ceramblerkasse;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.domain.*;
import ru.korbit.cecommon.store.StoresHelpersHolder;
import ru.korbit.cecommon.store.cacheregions.RamblerCacheRegion;
import ru.korbit.cecommon.utility.DateTimeUtils;
import ru.korbit.ceramblerkasse.api.impl.RamblerKassa;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

@Slf4j
@Service
public class AsyncLoader {

    private final RamblerKassa ramblerKassa;
    private final StoresHelpersHolder storesHelpersHolder;

    @Autowired
    public AsyncLoader(RamblerKassa ramblerKassa, StoresHelpersHolder storesHelpersHolder) {
        this.ramblerKassa = ramblerKassa;
        this.storesHelpersHolder = storesHelpersHolder;
    }

    @Async
    @Transactional
    public void loadShowtimes(Integer cityRamblerId, City currentCity) {
        log.info("Start load movie schedule from {}", currentCity.getName());
        ramblerKassa.getShowtimesCityLessDate(cityRamblerId,
                TimeUtility.getMaxDate(currentCity.getZoneOffset()))
                .forEach(ramblerShowtime -> {

                    if (ramblerShowtime.getPriceMin() == null || ramblerShowtime.getPriceMax() == null) {
                        return;
                    }

                    final Event currentEvent;
                    try {
                        currentEvent = storesHelpersHolder.getUsingCache(
                                ramblerShowtime.getEventId(),
                                RamblerCacheRegion.EVENT,
                                Event.class);
                    } catch (RuntimeException e) {
                        return;
                    }
                    val currentCinema = storesHelpersHolder.getUsingCache(
                            ramblerShowtime.getPlaceId(),
                            RamblerCacheRegion.CINEMA,
                            Cinema.class
                    );
                    val currentHall = storesHelpersHolder.putIfAbsent(
                            currentCinema.getName() + ramblerShowtime.getHallRamblerId(),
                            ramblerShowtime.toDBHall(currentCinema.getName()),
                            RamblerCacheRegion.HALL
                    );

                    if (!currentCinema.getHalls().contains(currentHall)) {
                        currentHall.setCinema(currentCinema);
                    }

                    val showtimeDb = ramblerShowtime.toDbShowtime(currentCity.getZoneOffset());
                    showtimeDb.setCinemaEvent((CinemaEvent) currentEvent);
                    showtimeDb.setHall(currentHall);
                    val currentShowtime = storesHelpersHolder.putIfAbsent(
                            ramblerShowtime.getShowtimeRamblerId(),
                            showtimeDb,
                            RamblerCacheRegion.SHOWTIME,
                            DateTimeUtils.getExpireMillis(showtimeDb.getStartTime())
                    );

                    val currentEventSchedule = storesHelpersHolder.putIfAbsent(
                            new Pair<>(ramblerShowtime.getEventId(), cityRamblerId),
                            new EventSchedule(currentEvent, currentCity),
                            RamblerCacheRegion.EVENT_SCHEDULE
                    );

                    if (currentShowtime.getStartTime().isBefore(currentEventSchedule.getStart())) {
                        currentEventSchedule.setStart(currentShowtime.getStartTime());
                    }
                    if (currentShowtime.getStartTime().isAfter(currentEventSchedule.getFinish())) {
                        currentEventSchedule.setFinish(currentShowtime.getStartTime());
                        storesHelpersHolder.updateExpire(
                                ramblerShowtime.getEventId(),
                                currentEvent.getId(),
                                RamblerCacheRegion.EVENT,
                                DateTimeUtils.getExpireMillis(currentEventSchedule.getFinish().plusDays(1)),
                                Event.class);
                    }
                });
        log.info("Finish load movie schedule from {}", currentCity.getName());
    }
}
