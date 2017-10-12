package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinemas")
@Data
public class Cinema {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "cinema_name")
    private String cinemaName;

    @Column(name = "place")
    private String place;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;
}

