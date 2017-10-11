package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "showtime")
public class Showtime {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "start_time")
    @Getter @Setter
    private Date startTime;

    @Column(name = "price")
    @Getter @Setter
    private int price;
}
