package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinema_event")
public class CinemaEvent extends Event {

    @ManyToMany(mappedBy = "events")
    @Getter @Setter
    private List<EventType> eventTypes;

    @ManyToMany
    @JoinTable(name = "cinema_cinema_event",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id", referencedColumnName = "id"))
    @Getter @Setter
    private List<Cinema> cinemas;
}
