package ru.korbit.ceramblerkasse;

import ru.korbit.cecommon.store.CacheRegion;

/**
 * Created by Artur Belogur on 08.11.17.
 */
public enum RamblerCacheRegion implements CacheRegion {
    CITY("rambler-city"),
    CINEMA("rambler-cinema"),
    HALL("rambler-hall"),
    SHOWTIME("rambler-showtime"),
    EVENT("rambler-event"),
    EVENT_TYPE("rambler-event-type");

    private final String region;

    RamblerCacheRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
