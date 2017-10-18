package ru.korbit.ceramblerkasse.services.filters;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExistCache {

    Optional<Long> check(Object ramblerId, RedisRegion redisRegion);

    void save(Object ramblerId, Object dbId, RedisRegion redisRegion);
}
