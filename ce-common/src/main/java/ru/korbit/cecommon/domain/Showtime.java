package ru.korbit.cecommon.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Entity
@Table(name = "showtime")
@IdClass(CinemaEventHall.class)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude={"cinemaEvent", "hall"})
public class Showtime {

    @Id
    @ManyToOne
    @JoinColumn(name = "cinema_event_id", referencedColumnName = "id")
    private CinemaEvent cinemaEvent;

    @Id
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;

    @NonNull
    @Column(name = "format")
    private String format;

    @NonNull
    @Id
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NonNull
    @Column(name = "price")
    private int price;

    @NonNull
    @Column(name = "rambler_id")
    private long ramblerId;
}
