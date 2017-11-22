package ru.korbit.ceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.packet.KindOfEvent;

import java.util.List;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Data
public class RCinemaEvent extends REvent {

    private Long duration;

    @JsonProperty("price_min")
    private Integer priceMin;

    @JsonProperty("price_max")
    private Integer priceMax;

    private List<RCinema> cinemas;

    public RCinemaEvent(CinemaEvent cinemaEvent, List<RCinema> cinemas,
                        Integer priceMin, Integer priceMax) {
        super(cinemaEvent);
        this.duration = cinemaEvent.getDuration().toMillis() / 1000;
        this.cinemas = cinemas;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
    }

    @JsonIgnore
    @Override
    public int getType() {
        return KindOfEvent.CINEMA.getKind();
    }
}
