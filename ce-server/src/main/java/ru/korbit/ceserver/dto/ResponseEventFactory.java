package ru.korbit.ceserver.dto;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.domain.SimpleEvent;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Component
public class ResponseEventFactory {

    private final ShowtimeDao showtimeDao;

    @Autowired
    public ResponseEventFactory(CinemaDao cinemaDao, ShowtimeDao showtimeDao) {
        this.showtimeDao = showtimeDao;
    }

    public REvent getResponseEvent(Event event, City city, ZonedDateTime now) {
        if (event instanceof CinemaEvent) {
            return getRCinemaEvent((CinemaEvent) event, city, now);
        }
        if (event instanceof SimpleEvent) {
            return new RSimpleEvent(event);
        }
        throw new RuntimeException("Not exist response for event = " + event.toString());
    }

    private RCinemaEvent getRCinemaEvent(CinemaEvent cinemaEvent, City city, ZonedDateTime now) {
        List<RCinema> cinemas = city.getCinemas()
                .stream()
                .map(cinema -> {
                    List<RHall> halls = cinema.getHalls()
                            .parallelStream()
                            .map(hall -> {
                                val showtimes = new ArrayList<RShowtime>();
                                showtimeDao.getByHallAndEventOnDay(cinemaEvent.getId(), hall.getId(), now)
                                        .forEach(showtime -> showtimes.add(new RShowtime(showtime)));

                                return new RHall(hall, showtimes);
                            })
                            .filter(hall -> !hall.getShowtimes().isEmpty())
                            .collect(Collectors.toList());
                    return new RCinema(cinema, halls);
                })
                .filter(cinema -> !cinema.getHalls().isEmpty())
                .collect(Collectors.toList());

        return new RCinemaEvent(cinemaEvent, cinemas);
    }
}
