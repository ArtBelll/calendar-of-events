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

    private int price;

    private Float latitude;

    private Float longitude;

    private String address;

    private String place;
}
