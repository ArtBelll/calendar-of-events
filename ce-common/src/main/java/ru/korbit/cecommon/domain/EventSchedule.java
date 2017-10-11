package ru.korbit.cecommon.domain;

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
@Table(name = "event_schedule")
public class EventSchedule {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter @Setter
    private long id;

    @ElementCollection
    @Column(name = "specific_days")
    @Getter @Setter
    private List<Date> specificDays;

    @ElementCollection
    @Column(name = "daysOfWeek")
    @Getter @Setter
    private List<DayOfWeek> daysOfWeek;

    @Column(name = "srart_time")
    @Getter @Setter
    private Date startTime;

    @Column(name = "finish_time")
    @Getter @Setter
    private Date finishTime;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @Getter @Setter
    private Event event;
}
