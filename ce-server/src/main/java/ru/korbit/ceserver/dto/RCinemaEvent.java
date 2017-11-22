package ru.korbit.ceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private List<RCinema> cinemas;

    public RCinemaEvent(CinemaEvent cinemaEvent, List<RCinema> cinemas) {
        super(cinemaEvent);
        this.duration = cinemaEvent.getDuration().toMillis() / 1000;
        this.cinemas = cinemas;
    }

    @JsonIgnore
    @Override
    public int getType() {
        return KindOfEvent.CINEMA.getKind();
    }
}
