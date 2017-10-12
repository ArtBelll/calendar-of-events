package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

/**
 * Created by Artur Belogur on 11.10.17.
 */
@Entity
@Table(name = "events_schedules")
@Data
public class EventSchedule {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ElementCollection
    @Column(name = "specific_days")
    private List<Date> specificDays;

    @ElementCollection
    @Column(name = "daysOfWeek")
    private List<DayOfWeek> daysOfWeek;

    @Column(name = "srart_time")
    private Date startTime;

    @Column(name = "finish_time")
    private Date finishTime;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
