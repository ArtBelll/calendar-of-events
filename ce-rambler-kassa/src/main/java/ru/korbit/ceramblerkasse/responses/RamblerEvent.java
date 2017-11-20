package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.val;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerEvent {

    @JsonProperty("ObjectID")
    private Integer eventRamblerId;

    @JsonProperty("Name")
    private String title;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Duration")
    private String duration;

    @JsonProperty("Thumbnail")
    private URL images;

    @JsonProperty("ClassType")
    private String type;

    @JsonProperty("Genre")
    private String genre;

    public CinemaEvent toDBEvent() {
        // TODO: load default images for movie
        if (images == null) {
            try {
                images = new URL("https://cdn.pixabay.com/photo/2017/01/20/11/04/icon-1994569_960_720.png");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        val duration = TimeUtility.durationFromString(this.duration);
        return new CinemaEvent(duration, title, description, images, genre);
    }
}
