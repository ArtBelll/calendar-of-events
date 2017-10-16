package ru.korbit.ceramblerkasse.responses;

import lombok.Data;
import lombok.val;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.EventType;
import ru.korbit.ceramblerkasse.utility.TimeUtility;

import java.net.URL;
import java.util.Collections;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerEvent {
    private long eventRamblerId;
    private String title;
    private String description;
    private String duration;
    private URL images;
    private EventType type;

    public CinemaEvent toDBEvent() {
        val duration = TimeUtility.durationFromString(this.duration);
        val eventTypes = Collections.singletonList(type);
        return new CinemaEvent(duration, title, description, images, eventTypes);
    }
}
