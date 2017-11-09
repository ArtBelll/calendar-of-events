package ru.korbit.cecommon.store;

import lombok.NonNull;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 07.11.17.
 */
public interface CacheStoreHelper {

    void addToCache(@NonNull Object cacheId,
                    @NonNull Serializable dbId,
                    @NonNull CacheRegion cacheRegion,
                    @NonNull Long millisToExpire);

    Optional<Serializable> getFromCache(@NonNull Object cacheId, @NonNull CacheRegion cacheRegion);
}
