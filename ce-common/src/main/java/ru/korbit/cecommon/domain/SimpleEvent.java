package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Artur Belogur on 10.10.17.
 */

@Entity
@Table(name = "simple_event")
public class SimpleEvent extends Event {

    @Column(name = "price")
    @Getter @Setter
    private int price;

    @Column(name = "start_date")
    @Getter @Setter
    private Date startDay;

    @Column(name = "finish_date")
    @Getter @Setter
    private Date finishDay;

    @Column(name = "latitude")
    @Getter @Setter
    private Float latitude;

    @Column(name = "longitude")
    @Getter @Setter
    private Float longitude;

    @Column(name = "address")
    @Getter @Setter
    private String address;

    @Column(name = "place")
    @Getter @Setter
    private String place;
}
