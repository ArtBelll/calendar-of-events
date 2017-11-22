package ru.korbit.ceserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.Showtime;

import java.time.ZoneOffset;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class RShowtime {

    private Long time;

    @JsonProperty("price_min")
    private Integer priceMin;

    @JsonProperty("price_max")
    private Integer priceMax;

    private String format;

    public RShowtime(Showtime showtime, ZoneOffset cityZone) {
        this.time = getLocalTimeInEpochSecond(showtime, cityZone);
        this.priceMin = showtime.getPriceMin();
        this.priceMax = showtime.getPriceMax();
        this.format = showtime.getFormat();
    }

    private Long getLocalTimeInEpochSecond(Showtime showtime, ZoneOffset cityZone) {
        return showtime.getStartTime().toEpochSecond()
                + cityZone.getTotalSeconds();
    }
}
