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

    private String format;

    public RShowtime(Showtime showtime, ZoneOffset cityZone) {
        this.time = getLocalTimeInEpochSecond(showtime, cityZone);
        this.price = showtime.getPrice();
        this.format = showtime.getFormat();
    }

    private Long getLocalTimeInEpochSecond(Showtime showtime, ZoneOffset cityZone) {
        return showtime.getStartTime().toEpochSecond()
                + cityZone.getTotalSeconds();
    }
}
