package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "showtimes")
@Data
public class Showtime {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "price")
    private int price;
}
