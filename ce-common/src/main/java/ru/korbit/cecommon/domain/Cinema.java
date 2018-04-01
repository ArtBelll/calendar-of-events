package ru.korbit.cecommon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "cinemas")
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Cinema implements GetIdable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String address;

    @NonNull
    @Column(nullable = false)
    private Float latitude;

    @NonNull
    @Column(nullable = false)
    private Float longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "cinema")
    private List<Hall> halls = new ArrayList<>();
}

