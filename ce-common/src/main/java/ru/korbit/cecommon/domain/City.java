package ru.korbit.cecommon.domain;

import lombok.*;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Cinema> cinemas = new ArrayList<>();
}
