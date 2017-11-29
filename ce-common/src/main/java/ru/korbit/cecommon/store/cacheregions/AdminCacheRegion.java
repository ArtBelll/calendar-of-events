package ru.korbit.cecommon.store.cacheregions;

import ru.korbit.cecommon.store.CacheRegion;

public enum AdminCacheRegion implements CacheRegion {

    ADMIN_EVENT("event");

    private static final String PREFIX = "admin-";

    private final String region;

    AdminCacheRegion(String region) {
        this.region = region;
    }

    @Override
    public String getRegion() {
        return PREFIX + region;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
