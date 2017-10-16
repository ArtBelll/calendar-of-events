package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Entity
@Table(name = "cinema_event_hall")
@IdClass(CinemaEventHall.class)
@Data
public class CinemaEventHallShowtime {

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
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NonNull
    @Column(name = "price")
    private int price;
}
