package ru.korbit.ceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.korbit.cecommon.domain.RecurringEvent;
import ru.korbit.cecommon.packet.KindOfEvent;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Data
public class RRecurringEvent extends REvent {

    private long time;
    private long duration;
    private long price;
    private float latitude;
    private float longitude;
    private Set<REventType> eventTypes;
    private String address;
    private String place;

    public RRecurringEvent(RecurringEvent recurringEvent, ZonedDateTime time, Duration duration) {
        super(recurringEvent);
        this.time = time.toEpochSecond();
        this.duration = duration.toMillis() / 1000;
        this.price = recurringEvent.getPrice();
        this.latitude = recurringEvent.getLatitude();
        this.longitude = recurringEvent.getLongitude();
        this.eventTypes = recurringEvent.getEventTypes()
                .stream()
                .map(REventType::new)
                .collect(Collectors.toSet());
        this.address = recurringEvent.getAddress();
        this.place = recurringEvent.getPlace();

    }

    @JsonIgnore
    @Override
    public int getType() {
        return KindOfEvent.SIMPLE.getKind();
    }
}
