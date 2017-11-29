package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "schedules")
@IdClass(CityEvent.class)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EventSchedule implements GetIdable {

    private static ZonedDateTime MAX_TIME = ZonedDateTime.parse("9999-12-31T23:59:59+00:00");
    private static ZonedDateTime MIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneOffset.UTC);

    @NonNull
    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @NonNull
    @Id
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @NonNull
    @Column(name = "start", nullable = false)
    private ZonedDateTime start = MAX_TIME;

    @NonNull
    @Column(name = "finish",nullable = false)
    private ZonedDateTime finish = MIN_TIME;


    @Override
    public Serializable getId() {
        return new CityEvent(event.getId(), city.getId());
    }
}
