package ru.korbit.cecommon.packet;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Created by Artur Belogur on 19.10.17.
 */
@Data
@RequiredArgsConstructor
public class DateRange {
    @NonNull private ZonedDateTime start;
    @NonNull private ZonedDateTime finish;
}
