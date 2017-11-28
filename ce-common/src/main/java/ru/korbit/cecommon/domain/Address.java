package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Data
public class Address {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private String street;

    private String house;

    private int postcode;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}
