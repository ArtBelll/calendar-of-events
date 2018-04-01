package ru.korbit.cecommon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Entity
@Table(name = "showtime")
@IdClass(CinemaEventHall.class)
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Showtime implements GetIdable {

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name = "cinema_event_id", referencedColumnName = "id")
    private CinemaEvent cinemaEvent;

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;

    @NonNull
    private String format;

    @NonNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NonNull
    @Column(name = "price_min", nullable = false)
    private Integer priceMin;

    @NonNull
    @Column(name = "price_max", nullable = false)
    private Integer priceMax;

    @JsonIgnore
    @NonNull
    @Id
    @Column(name = "rambler_id", nullable = false, unique = true)
    private Long ramblerId;

    @Override
    public Serializable getId() {
        return new CinemaEventHall(cinemaEvent.getId(), hall.getId(), ramblerId);
    }
}
