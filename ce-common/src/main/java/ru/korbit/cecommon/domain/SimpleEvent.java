package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Artur Belogur on 10.10.17.
 */

@Entity
@Table(name = "simple_events")
@Data
public class SimpleEvent extends Event {

    @Column(name = "price")
    private int price;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "place")
    private String place;
}
