package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Event {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private URL imageURL;

    @Column(name = "thumb_image_url")
    private URL thumbImageURL;

    @Column(name = "start_date")
    private LocalDate startDay;

    @Column(name = "finish_date")
    private LocalDate finishDay;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ElementCollection
    @Column(name = "links")
    private List<URL> links;

    @ManyToMany(mappedBy = "events")
    private List<EventType> eventTypes;

    @OneToMany(mappedBy = "event")
    private List<EventSchedule> eventSchedules;
}
