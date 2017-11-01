package ru.korbit.ceramblerkasse.services.store.impl;

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.ceramblerkasse.services.store.CacheStore;
import ru.korbit.ceramblerkasse.services.store.Region;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
//TODO
@Component
public class CacheStoreImpl implements CacheStore {

    private final RedissonClient redissonClient;

    @Autowired
    public CacheStoreImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Optional<Serializable> check(Object ramblerId, Region redisRegion) {
        return Optional.ofNullable((Serializable) redissonClient
                .getLocalCachedMap(redisRegion.getRegion(), LocalCachedMapOptions.defaults())
                .get(ramblerId));
    }

    @Override
    public void save(Object ramblerId, Serializable dbId, Region redisRegion) {
        redissonClient
                .getLocalCachedMap(redisRegion.getRegion(), LocalCachedMapOptions.defaults())
                .fastPut(ramblerId, dbId);
    }
}
