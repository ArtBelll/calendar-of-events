package ru.korbit.cecommon.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "action_schedules")
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ActionSchedule {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String cron;

    @NonNull
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "recurring_event_id")
    private RecurringEvent recurringEvent;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
