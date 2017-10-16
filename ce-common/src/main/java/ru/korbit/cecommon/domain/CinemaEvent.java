package ru.korbit.cecommon.domain;

import lombok.*;

import javax.persistence.*;
import java.net.URL;
import java.time.Duration;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinema_events")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CinemaEvent extends Event {

    @Column(name = "duration")
    @NonNull private Duration duration;

    @ManyToMany
    @JoinTable(name = "cinema_cinema_event",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id", referencedColumnName = "id"))
    private List<Cinema> cinemas;

    @OneToMany(mappedBy = "cinemaEvent")
    private List<CinemaEventHallShowtime> cinemaEventHallShowtimeList;

    public CinemaEvent(Duration duration,
                       String title,
                       String description,
                       URL imageURL) {
        super(title, description, imageURL);
        this.duration = duration;
    }
}
