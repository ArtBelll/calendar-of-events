package ru.korbit.cecommon.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter @Setter
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
