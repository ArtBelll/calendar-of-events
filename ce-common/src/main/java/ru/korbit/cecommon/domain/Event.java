package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class Event {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    @NonNull private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @NonNull private String description;

    @Column(name = "image_url")
    @NonNull private URL imageURL;

    @Column(name = "thumb_image_url")
    private URL thumbImageURL;

    @Column(name = "start_date")
    private LocalDate startDay = LocalDate.parse("9999-12-31");

    @Column(name = "finish_date")
    private LocalDate finishDay = LocalDate.ofEpochDay(0);

    @ManyToOne
    @JoinColumn(name = "city_id")
    @NonNull private City city;

    @ElementCollection
    @Column(name = "links")
    private List<URL> links;

    @ManyToMany(mappedBy = "events")
    private List<EventType> eventTypes = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventSchedule> eventSchedules;
}
