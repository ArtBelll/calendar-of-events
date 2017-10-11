package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinema")
public class Cinema {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "cinema_name")
    @Getter @Setter
    private String cinemaName;

    @Column(name = "place")
    @Getter @Setter
    private String place;

    @Column(name = "address")
    @Getter @Setter
    private String address;

    @Column(name = "latitude")
    @Getter @Setter
    private Float latitude;

    @Column(name = "longitude")
    @Getter @Setter
    private Float longitude;
}

