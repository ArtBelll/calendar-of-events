package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "action_schedules")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ActionSchedule {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String cron;

    @NonNull
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "recurring_event_id")
    private RecurringEvent recurringEvent;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
