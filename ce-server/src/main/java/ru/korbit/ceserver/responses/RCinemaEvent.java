package ru.korbit.ceserver.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.packet.KindOfEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Data
public class RCinemaEvent extends REvent {

    private long id;

    private String title;

    private String description;

    private Long duration;

    @JsonProperty("image_url")
    private URL imageUrl;

    private List<URL> links = new ArrayList<>();

    private List<Cinema> cinemas = new ArrayList<>();

    public RCinemaEvent(CinemaEvent cinemaEvent, List<Cinema> cinemas) {
        this.id = cinemaEvent.getId();
        this.title = cinemaEvent.getTitle();
        this.description = cinemaEvent.getDescription();
        this.duration = cinemaEvent.getDuration().toMillis();
        this.imageUrl = cinemaEvent.getImageURL();
        this.links.addAll(cinemaEvent.getLinks());
        this.cinemas.addAll(cinemas);
    }

    @Override
    public int getType() {
        return KindOfEvent.CINEMA.getKind();
    }
}
