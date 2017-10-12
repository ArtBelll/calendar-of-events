package ru.korbit.cecommon.domain;

import lombok.Data;
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
@Data
public class SimpleEvent extends Event {

    @Column(name = "price")
    private int price;

    @Column(name = "start_date")
    private Date startDay;

    @Column(name = "finish_date")
    private Date finishDay;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "place")
    private String place;
}
