package ru.korbit.cecommon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "halls")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"showtimeList"})
public class Hall implements GetIdable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "rambler_id")
    private String ramblerId;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    private List<Showtime> showtimeList;
}
