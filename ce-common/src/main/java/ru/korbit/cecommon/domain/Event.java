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

    @NonNull
    private String additionally;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    private List<EventSchedule> eventSchedules = new ArrayList<>();

    @ElementCollection
    @Column(name = "links")
    private List<URL> links = new ArrayList<>();

    @ManyToMany(mappedBy = "events")
    private List<EventType> eventTypes = new ArrayList<>();
}
