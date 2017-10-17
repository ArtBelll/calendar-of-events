package ru.korbit.ceramblerkasse;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.services.RamblerKassaService;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.services.api.impl.RamblerKassa;
import ru.korbit.ceramblerkasse.services.filters.RedisRegion;
import ru.korbit.ceramblerkasse.services.filters.impl.CheckerExistCacheImpl;
import ru.korbit.ceramblerkasse.services.filters.impl.CheckerExistDbImpl;

import java.util.List;
import java.util.Optional;

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
    public void load() {
        List<RamblerCity> cities = ramblerKassa.getCities();
        //TODO for all city
        cities.stream()
                .filter(city -> city.getCityRamblerId().equals(KALININGRAD_ID))
                .forEach(city -> {
                    final City currentCity;

                    val cityDbId = checkerExistInCache.check(city.getCityRamblerId().longValue(),
                            RedisRegion.CITY);
                    if (cityDbId.isPresent()) {
                        currentCity = checkerExistInDb.getObject(cityDbId.get(), City.class);
                    }
                    else {
                        currentCity = checkerExistInDb.checkAndSave(city.toDBCity());
                        checkerExistInCache.save(currentCity.getId(),
                                city.getCityRamblerId().longValue(), RedisRegion.CITY);
                    }

                    ramblerKassa.getCinemasAtCity(city.getCityRamblerId())
                            .forEach(cinema -> {
                                val cinemaDbId = checkerExistInCache
                                        .check(cinema.getCinemaRamblerId().longValue(), RedisRegion.CINEMA);
                                if (!cinemaDbId.isPresent()) {
                                    val cinemaDb = cinema.toBDCinema();
                                    cinemaDb.setCity(currentCity);
                                    checkerExistInDb.checkAndSave(cinemaDb);
                                }
                            });
                });
    }
}
