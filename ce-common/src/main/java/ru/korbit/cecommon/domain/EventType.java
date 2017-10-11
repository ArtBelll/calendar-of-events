package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "event_types")
@Inheritance(strategy =InheritanceType.TABLE_PER_CLASS)
public class EventType {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @ManyToMany
    @JoinTable(name = "event_event_types",
            joinColumns = @JoinColumn(name = "event_type_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    @Getter @Setter
    private List<Event> events;
}
