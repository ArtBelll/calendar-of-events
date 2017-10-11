package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
public class Hall {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @ManyToMany
    @JoinTable(name = "hall_showtime",
            joinColumns = @JoinColumn(name = "hall_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "showtime_id", referencedColumnName = "id"))
    @Getter @Setter
    private List<Cinema> cinemas;
}
