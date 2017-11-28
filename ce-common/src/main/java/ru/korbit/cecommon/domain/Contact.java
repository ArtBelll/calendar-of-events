package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Data
public class Contact {

    @Id
    @GeneratedValue
    private long id;

    private String type;

    private String value;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}
