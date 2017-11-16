package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "organisations")
public class Organisation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
