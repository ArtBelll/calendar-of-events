package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinema_events")
@Data
public class CinemaEvent extends Event {

    @Column(name = "duration")
    private Duration duration;

    @ManyToMany(mappedBy = "events")
    private List<EventType> eventTypes;

    @ManyToMany
    @JoinTable(name = "cinema_cinema_event",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id", referencedColumnName = "id"))
    private List<Cinema> cinemas;
}
