package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.korbit.cecommon.packet.GetIdable;

import javax.persistence.*;
import java.net.URL;
import java.time.*;
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
public abstract class Event implements GetIdable {

    private static ZonedDateTime MAX_TIME = ZonedDateTime.parse("9999-12-31T23:59:59+00:00");
    private static ZonedDateTime MIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneOffset.UTC);

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NonNull private String title;

    @Column(columnDefinition = "TEXT")
    @NonNull private String description;

    @Column(name = "image_url", nullable = false)
    @NonNull private URL imageURL;

    @Column(name = "thumb_image_url")
    private URL thumbImageURL;

    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDay = MAX_TIME;

    @Column(name = "finish_date", nullable = false)
    private ZonedDateTime finishDay = MIN_TIME;

    @NonNull
    private String additionally;

    @ManyToMany(mappedBy = "events")
    private List<City> cities = new ArrayList<>();

    @ElementCollection
    @Column(name = "links")
    private List<URL> links = new ArrayList<>();

    @ManyToMany(mappedBy = "events")
    private List<EventType> eventTypes = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventSchedule> eventSchedules;
}
