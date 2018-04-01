package ru.korbit.cecommon.domain;

import lombok.*;

import java.io.Serializable;

/**
 * Created by Artur Belogur on 16.10.17.
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class CinemaEventHall implements Serializable {
    @NonNull private Long cinemaEvent;
    @NonNull private Long hall;
    @NonNull private Long ramblerId;
}
