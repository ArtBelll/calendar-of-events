package ru.korbit.cecommon.services;

import lombok.NonNull;

import java.time.ZoneOffset;

/**
 * Created by Artur Belogur on 13.11.17.
 */
public interface TimeZone {

    ZoneOffset getZoneOffsetByLatlng(@NonNull Float lat, @NonNull Float lng);
}
