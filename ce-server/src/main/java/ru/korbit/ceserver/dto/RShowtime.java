package ru.korbit.ceserver.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Showtime;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class RShowtime {

    private Long time;

    private int price;

    private String format;

    public RShowtime(Showtime showtime) {
        this.time = showtime.getStartTime().toEpochSecond();
        this.price = showtime.getPrice();
        this.format = showtime.getFormat();
    }
}
