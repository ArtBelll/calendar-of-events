package ru.korbit.cecommon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@Entity
@Table(name = "events")
public abstract class Event {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @Column(name = "title")
    @Getter @Setter
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Getter @Setter
    private String description;

    @Column(name = "image_url")
    @Getter @Setter
    private URL imageURL;

    @Column(name = "thumb_image_url")
    @Getter @Setter
    private URL thumbImageURL;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @Getter @Setter
    private City city;

    @ElementCollection
    @Column(name = "links")
    @Getter @Setter
    private List<URL> links;

    @ManyToMany(mappedBy = "events")
    @Getter @Setter
    private List<EventType> eventTypes;

    @OneToMany(mappedBy = "event")
    private List<EventSchedule> eventSchedules;
}
