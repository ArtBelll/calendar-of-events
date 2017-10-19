package ru.korbit.ceramblerkasse.services.filters;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExistCache {

    Optional<Serializable> check(Object ramblerId, RedisRegion redisRegion);

    void save(Object ramblerId, Serializable dbId, RedisRegion redisRegion);
}
