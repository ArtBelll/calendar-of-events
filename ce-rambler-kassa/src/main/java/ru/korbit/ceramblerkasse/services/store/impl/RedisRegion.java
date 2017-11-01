package ru.korbit.ceramblerkasse.services.store.impl;

import ru.korbit.ceramblerkasse.services.store.Region;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public enum RedisRegion implements Region {
    CITY("city"),
    CINEMA("cinema"),
    HALL("hall"),
    SHOWTIME("showtime"),
    EVENT("event"),
    EVENT_TYPE("event_type");

    private final String region;

    RedisRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
