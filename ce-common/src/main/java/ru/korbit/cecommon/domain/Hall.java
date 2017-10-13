package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "halls")
@Data
public class Hall {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToMany
    @JoinTable(name = "hall_showtime",
            joinColumns = @JoinColumn(name = "hall_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "showtime_id", referencedColumnName = "id"))
    private List<Cinema> cinemas;
}
