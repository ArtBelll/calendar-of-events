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
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"halls"})
public class Cinema implements GetIdable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "cinema_name")
    private String name;

    @Column(name = "place")
    private String place;

    @NonNull
    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "latitude")
    private Float latitude;

    @NonNull
    @Column(name = "longitude")
    private Float longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "cinema")
    private List<Hall> halls = new ArrayList<>();
}

