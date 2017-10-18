package ru.korbit.ceramblerkasse.services.filters.impl;

import lombok.val;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.ceramblerkasse.services.filters.CheckerExistCache;
import ru.korbit.ceramblerkasse.services.filters.RedisRegion;

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
    public Optional<Long> check(Object ramblerId, RedisRegion redisRegion) {
        return Optional.ofNullable((Long) redissonClient
                .getLocalCachedMap(redisRegion.getRegion(), LocalCachedMapOptions.defaults())
                .get(ramblerId));
    }

    @Override
    public void save(Object ramblerId, Object dbId, RedisRegion redisRegion) {
        redissonClient
                .getLocalCachedMap(redisRegion.getRegion(), LocalCachedMapOptions.defaults())
                .fastPut(ramblerId, dbId);
    }
}
