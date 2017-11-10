package ru.korbit.ceserver.dto;

import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.domain.SimpleEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Component
public class ResponseEventFactory {

    private final CinemaDao cinemaDao;

    private final ShowtimeDao showtimeDao;

    @Autowired
    public ResponseEventFactory(CinemaDao cinemaDao, ShowtimeDao showtimeDao) {
        this.cinemaDao = cinemaDao;
        this.showtimeDao = showtimeDao;
    }

    public REvent getResponseEvent(Event event, Long cityId) {
        if (event instanceof CinemaEvent) {
            return getRCinemaEvent((CinemaEvent) event, cityId);
        }
        if (event instanceof SimpleEvent) {
            return new RSimpleEvent(event);
        }
        throw new RuntimeException("Not exist response for event = " + event.toString());
    }

    private RCinemaEvent getRCinemaEvent(CinemaEvent cinemaEvent, Long cityId) {
        val now = LocalDateTime.now();
        List<RCinema> cinemas = cinemaDao.getByEventOnDay(cityId, cinemaEvent.getId(), now)
                .map(cinema -> {
                    Hibernate.initialize(cinema.getHalls());
                    List<RHall> halls = cinema.getHalls()
                            .stream()
                            .map(hall -> {
                                val showtimes = showtimeDao.getByHallOnDay(hall.getId(), now)
                                        .map(RShowtime::new)
                                        .collect(Collectors.toList());
                                return new RHall(hall, showtimes);
                            })
                            .collect(Collectors.toList());
                    return new RCinema(cinema, halls);
                })
                .collect(Collectors.toList());

        return new RCinemaEvent(cinemaEvent, cinemas);
    }
}
