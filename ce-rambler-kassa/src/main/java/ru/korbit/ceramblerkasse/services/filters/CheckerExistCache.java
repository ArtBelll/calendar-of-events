package ru.korbit.ceramblerkasse.services.filters;

import javafx.util.Pair;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.domain.CinemaEventHallShowtime;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.Hall;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExistCache {

    Optional<Long> check(Long ramblerId, RedisRegion redisRegion);

    void save(Long ramblerId, Long dbId, RedisRegion redisRegion);
}
