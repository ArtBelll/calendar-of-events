package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CinemaEventHall implements Serializable {
    @NonNull private long cinemaEvent;
    @NonNull private long hall;
    @NonNull private LocalDateTime startTime;
}
