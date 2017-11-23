package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */

@Entity
@Table(name = "simple_events")
@Data
public class SimpleEvent extends Event {

    private int price;

    private Float latitude;

    private Float longitude;

    private String address;

    private String place;

    @OneToMany(mappedBy = "simpleEvent")
    private List<ActionSchedule> actionSchedules = new ArrayList<>();

    @ElementCollection
    private List<ZonedDateTime> dateExceptions = new ArrayList<>();

    @ElementCollection
    private List<ZonedDateTime> noneAction = new ArrayList<>();
}
