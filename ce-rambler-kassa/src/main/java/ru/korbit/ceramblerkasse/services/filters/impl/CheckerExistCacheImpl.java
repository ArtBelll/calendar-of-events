package ru.korbit.ceramblerkasse.services.filters.impl;

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.ceramblerkasse.services.filters.CheckerExistCache;
import ru.korbit.ceramblerkasse.services.filters.RedisRegion;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
//TODO
@Component
public class CheckerExistCacheImpl implements CheckerExistCache {

    private final RedissonClient redissonClient;

    @Autowired
    public CheckerExistCacheImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Optional<Serializable> check(Object ramblerId, RedisRegion redisRegion) {
        return Optional.ofNullable((Serializable) redissonClient
                .getLocalCachedMap(redisRegion.getRegion(), LocalCachedMapOptions.defaults())
                .get(ramblerId));
    }

    @Override
    public void save(Object ramblerId, Serializable dbId, RedisRegion redisRegion) {
        redissonClient
                .getLocalCachedMap(redisRegion.getRegion(), LocalCachedMapOptions.defaults())
                .fastPut(ramblerId, dbId);
    }
}
