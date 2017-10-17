package ru.korbit.ceramblerkasse.services.filters;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public enum RedisRegion {
    CITY("city"),
    CINEMA("cinema"),
    HALL("hall"),
    SHOWTIME("showtime");

    private final String region;

    RedisRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
