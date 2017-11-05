package ru.korbit.ceserver.responses;

import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.Cinema;
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

    public REvent getResponseEvent(Event event) {
        if (event instanceof CinemaEvent) {
            return getRCinemaEvent((CinemaEvent) event);
        }
        if (event instanceof SimpleEvent) {
            return new RSimpleEvent();
        }
        throw new RuntimeException("Not exist response for event = " + event.toString());
    }

    private RCinemaEvent getRCinemaEvent(CinemaEvent cinemaEvent) {
        val now = LocalDateTime.now();
        List<Cinema> cinemas = cinemaDao.getByEventOnDay(cinemaEvent.getId(), now)
                .peek(cinema -> {
                    Hibernate.initialize(cinema.getHalls());
                    cinema.getHalls().forEach(hall -> {
                        val showtimeList = showtimeDao.getByHallOnDay(hall.getId(),
                                now).collect(Collectors.toList());
                        hall.setShowtimeList(showtimeList);
                    });
                })
                .collect(Collectors.toList());

        return new RCinemaEvent(cinemaEvent, cinemas);
    }
}
