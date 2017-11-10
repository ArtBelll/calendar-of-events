package ru.korbit.ceserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.Event;

import java.net.URL;
import java.util.List;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Data
public abstract class REvent {

    private Long id;

    private String title;

    private String description;

    @JsonProperty("image_url")
    private URL imageURL;

    private List<URL> links;

    public REvent(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.imageURL = event.getImageURL();
        this.links = event.getLinks();
    }

    public abstract int getType();
}
