package ru.korbit.cecommon.domain;

import lombok.*;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"events", "cinemas"})
public class City implements GetIdable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "time_zone")
    private ZoneOffset zoneOffset;

    @ManyToMany
    @JoinTable(name = "city_event",
            joinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    private List<Cinema> cinemas = new ArrayList<>();
}
