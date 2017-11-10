package ru.korbit.ceserver.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Showtime;

import java.time.ZoneOffset;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class RShowtime {

    private Long time;

    private int price;

    public RShowtime(Showtime showtime) {
        this.time = showtime.getStartTime().toEpochSecond(ZoneOffset.UTC);
        this.price = showtime.getPrice();
    }
}
