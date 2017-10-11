package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "city")
    @Getter @Setter
    private List<Event> events;
}
