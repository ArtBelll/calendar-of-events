package ru.korbit.cecommon.domain;

import lombok.*;

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
public class Cinema {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

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

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "cinema")
    private List<Hall> halls = new ArrayList<>();
}

