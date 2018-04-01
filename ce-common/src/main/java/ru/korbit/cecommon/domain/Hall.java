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
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"showtimeList"})
public class Hall implements GetIdable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(name = "third_party_id", unique = true, nullable = false)
    private String thirdPartyId;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    private List<Showtime> showtimeList;
}
