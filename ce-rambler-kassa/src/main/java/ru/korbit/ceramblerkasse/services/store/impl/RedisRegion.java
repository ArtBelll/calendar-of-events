package ru.korbit.ceramblerkasse.services.store.impl;

import ru.korbit.ceramblerkasse.services.store.Region;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public enum RedisRegion implements Region {
    CITY("rambler-city"),
    CINEMA("rambler-cinema"),
    HALL("rambler-hall"),
    SHOWTIME("rambler-showtime"),
    EVENT("rambler-event"),
    EVENT_TYPE("rambler-event-type");

    private final String region;

    RedisRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
