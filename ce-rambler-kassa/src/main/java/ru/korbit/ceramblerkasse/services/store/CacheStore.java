package ru.korbit.ceramblerkasse.services.store;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CacheStore {

    Optional<Serializable> check(Object ramblerId, Region redisRegion);

    void save(Object ramblerId, Serializable dbId, Region redisRegion);
}
