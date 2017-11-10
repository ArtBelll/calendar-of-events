package ru.korbit.ceserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.Event;

import java.net.URL;

/**
 * Created by Artur Belogur on 31.10.17.
 */
@Data
public class RGeneralEvent {

    private long id;

    private String title;

    @JsonProperty("thumb_image_url")
    private URL thumbImageUrl;

    @JsonProperty("image_url")
    private URL imageUrl;

    private String place;

    public RGeneralEvent(Event event, String place) {
        id = event.getId();
        title = event.getTitle();
        thumbImageUrl = event.getThumbImageURL();
        imageUrl = event.getImageURL();
        this.place = place;
    }
}
