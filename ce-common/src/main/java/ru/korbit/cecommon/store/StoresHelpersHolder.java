package ru.korbit.cecommon.store;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.domain.*;
import ru.korbit.cecommon.packet.GetIdable;
import ru.korbit.cecommon.store.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Artur Belogur on 07.11.17.
 */
@Slf4j
@Service
public class StoresHelpersHolder {

    private Map<Class<?>, CommonStoreHelper> holder = new HashMap<>();

    @Autowired
    public StoresHelpersHolder(CityStoreHelper cityStoreHelper,
                               CinemaStoreHelper cinemaStoreHelper,
                               EventStoreHelper eventStoreHelper,
                               HallStoreHelper hallStoreHelper,
                               ShowtimeStoreHelper showtimeStoreHelper,
                               EventTypeStoreHelper eventTypeStoreHelper) {
        holder.put(City.class, cityStoreHelper);
        holder.put(Cinema.class, cinemaStoreHelper);
        holder.put(Event.class, eventStoreHelper);
        holder.put(CinemaEvent.class, eventStoreHelper);
        holder.put(SimpleEvent.class, eventStoreHelper);
        holder.put(Hall.class, hallStoreHelper);
        holder.put(Showtime.class, showtimeStoreHelper);
        holder.put(EventType.class, eventTypeStoreHelper);
    }

    private <T> T putIfAbsent(Object cacheId,
                              T objectDb,
                              CommonStoreHelper<T> storeHelper,
                              CacheRegion cacheRegion) {

        Optional<T> currentObject = Optional.empty();

        val dbId = storeHelper.getFromCache(cacheId, cacheRegion);

        if (dbId.isPresent()) {
            currentObject = storeHelper.getFromDb(dbId.get());
        }
        if (!currentObject.isPresent()) {
            currentObject = storeHelper.searchFromDb(objectDb);
            if (!currentObject.isPresent()) {
                currentObject = Optional.of(storeHelper.addToDb(objectDb));
                log.info("Add entity: {}", currentObject.orElse(null));
            }
            currentObject.ifPresent(currentObjectDb ->
                    storeHelper.addToCache(cacheId, ((GetIdable) currentObjectDb).getId(), cacheRegion));
        }

        return currentObject.orElseThrow(RuntimeException::new);
    }

    @SuppressWarnings("unchecked")
    public <T> T putIfAbsent(Object cacheId, T object, CacheRegion cacheRegion) {
        return putIfAbsent(cacheId, object,
                (CommonStoreHelper<T>) holder.get(object.getClass()), cacheRegion);
    }

    @SuppressWarnings("unchecked")
    public <T> T getUsingCache(Object cacheId, CacheRegion cacheRegion, Class<T> tClass) {
        val storeHelper =  holder.get(tClass);
        return (T) storeHelper.getFromCache(cacheId, cacheRegion)
                .map(dbId -> storeHelper.getFromDb(dbId).orElse(null))
                .orElseThrow(() -> {
                    log.error("Cache error. id = {}, region = {}", cacheId, cacheRegion.getRegion());
                    return new RuntimeException("Cache error");
                });
    }

    @SuppressWarnings("unchecked")
    public void updateDb(Object object) {
        holder.get(object.getClass()).updateInDb(object);
        log.info("Update entity: {}", object);
    }
}
