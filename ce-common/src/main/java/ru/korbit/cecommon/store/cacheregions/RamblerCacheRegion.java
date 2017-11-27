package ru.korbit.cecommon.store.cacheregions;

import ru.korbit.cecommon.store.CacheRegion;

/**
 * Created by Artur Belogur on 08.11.17.
 */
public enum RamblerCacheRegion implements CacheRegion {

    CITY("city"),
    CINEMA("cinema"),
    HALL("hall"),
    SHOWTIME("showtime"),
    EVENT("event"),
    EVENT_SCHEDULE("event-schedule"),
    EVENT_TYPE("event-type");

    private static final String PREFIX = "rambler-";

    private final String region;

    RamblerCacheRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return PREFIX + region;
    }

    public String getPrefix() {
        return PREFIX;
    }
}

