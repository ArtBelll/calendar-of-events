package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Data
@EqualsAndHashCode(exclude = "organisation")
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String city;

    private String street;

    private String house;

    private Integer postcode;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}
