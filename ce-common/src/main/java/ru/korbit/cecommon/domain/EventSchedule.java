package ru.korbit.cecommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "schedules")
@IdClass(CinemaEventHall.class)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EventSchedule {

    private static ZonedDateTime MAX_TIME = ZonedDateTime.parse("9999-12-31T23:59:59+00:00");
    private static ZonedDateTime MIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneOffset.UTC);

    @Id
    @GeneratedValue
    private Long eventId;

    @Id
    @GeneratedValue
    private Long cityId;

    @NonNull
    @Column(name = "start", nullable = false)
    private ZonedDateTime start = MAX_TIME;

    @NonNull
    @Column(name = "finish",nullable = false)
    private ZonedDateTime finish = MIN_TIME;
}
