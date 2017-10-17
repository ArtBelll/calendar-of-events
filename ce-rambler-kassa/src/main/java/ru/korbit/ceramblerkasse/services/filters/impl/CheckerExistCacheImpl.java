package ru.korbit.ceramblerkasse.services.filters.impl;

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
    public Optional<Long> check(Long ramblerId, RedisRegion redisRegion) {
//        return Optional.of((Long) redissonClient.getMap(redisRegion.getRegion()).get(ramblerId));
        return Optional.empty();
    }

    @Override
    public void save(Long ramblerId, Long dbId, RedisRegion redisRegion) {
//        redissonClient.getMap(redisRegion.getRegion()).put(ramblerId, dbId);
    }
}
