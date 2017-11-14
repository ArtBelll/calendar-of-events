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
        this.time = getLocalTimeInEpochSecond(showtime);
        this.price = showtime.getPrice();
        this.format = showtime.getFormat();
    }

    private Long getLocalTimeInEpochSecond(Showtime showtime) {
        return showtime.getStartTime().toEpochSecond()
                + showtime.getStartTime().getOffset().getTotalSeconds();
    }
}
