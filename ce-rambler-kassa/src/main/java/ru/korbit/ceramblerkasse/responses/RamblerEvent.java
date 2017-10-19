package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.val;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.EventType;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

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

    private City city;

    public CinemaEvent toDBEvent() {
        val duration = TimeUtility.durationFromString(this.duration);
        return new CinemaEvent(duration, title, description, images, city);
    }

    public EventType toDBEventType() {
        return new EventType(type);
    }
}
