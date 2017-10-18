package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.services.RamblerKassaService;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.services.api.impl.RamblerKassa;
import ru.korbit.ceramblerkasse.services.filters.RedisRegion;
import ru.korbit.ceramblerkasse.services.filters.impl.CheckerExistCacheImpl;
import ru.korbit.ceramblerkasse.services.filters.impl.CheckerExistDbImpl;

import java.time.LocalDate;
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

    private final LocalDate MAX_DATE = LocalDate.now().plusWeeks(1);

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
                    final City currentCity;

                    val cityDbId = checkerExistInCache.check(ramblerCity.getCityRamblerId().longValue(),
                            RedisRegion.CITY);
                    if (cityDbId.isPresent()) {
                        currentCity = checkerExistInDb.getObject(cityDbId.get(), City.class);
                    } else {
                        currentCity = checkerExistInDb.checkAndSave(ramblerCity.toDBCity());
                        checkerExistInCache.save(currentCity.getId(),
                                ramblerCity.getCityRamblerId().longValue(), RedisRegion.CITY);
                    }

                    ramblerKassa.getCinemasAtCity(ramblerCity.getCityRamblerId())
                            .forEach(ramblerCinema -> {
                                val cinemaDbId = checkerExistInCache
                                        .check(ramblerCinema.getCinemaRamblerId().longValue(), RedisRegion.CINEMA);
                                if (!cinemaDbId.isPresent()) {
                                    val cinemaDb = ramblerCinema.toBDCinema();
                                    cinemaDb.setCity(currentCity);
                                    checkerExistInDb.checkAndSave(cinemaDb);
                                }
                            });

                    ramblerKassa.getEventsLessDateAtCity(ramblerCity.getCityRamblerId(), MAX_DATE)
                            .forEach(ramblerEvent -> {
                                final Event currentEvent;

                                val eventDbId = checkerExistInCache
                                        .check(ramblerEvent.getEventRamblerId().longValue(), RedisRegion.EVENT);
                                if (eventDbId.isPresent()) {
                                    currentEvent = checkerExistInDb.getObject(eventDbId.get(), Event.class);
                                }
                                else {
                                    currentEvent = checkerExistInDb.checkAndSave(ramblerEvent.toDBEvent());
                                    checkerExistInCache.save(currentEvent.getId(),
                                            ramblerEvent.getEventRamblerId().longValue(), RedisRegion.EVENT);
                                }
                            });


                });
    }
}
