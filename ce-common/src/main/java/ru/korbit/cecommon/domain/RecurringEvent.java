package ru.korbit.cecommon.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur Belogur on 10.10.17.
 */

@Entity
@Table(name = "recurring_events")
@Data
public class RecurringEvent extends Event {

    private int price;

    private Float latitude;

    private Float longitude;

    private String address;

    private String place;

    @OneToMany(mappedBy = "recurringEvent", cascade = CascadeType.REMOVE)
    private List<ActionSchedule> actionSchedules = new ArrayList<>();

    @ElementCollection
    private List<ZonedDateTime> datesException = new ArrayList<>();

    @ElementCollection
    private List<ZonedDateTime> noneAction = new ArrayList<>();
}
