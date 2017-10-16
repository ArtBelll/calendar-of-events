package ru.korbit.cecommon.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Data
public class CinemaEventHall implements Serializable {
    private long cinemaEvent;
    private long hall;
}
