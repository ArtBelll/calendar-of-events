package ru.korbit.cecommon.domain;

import lombok.*;

import javax.persistence.*;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinema_events")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"showtimeList"})
public class CinemaEvent extends Event {

    @NonNull
    @Column(nullable = false)
    private Duration duration;

    @OneToMany(mappedBy = "cinemaEvent")
    private List<Showtime> showtimeList = new ArrayList<>();

    public CinemaEvent(Duration duration,
                       String title,
                       String description,
                       URL imageURL) {
        super(title, description, imageURL);
        this.duration = duration;
    }
}
