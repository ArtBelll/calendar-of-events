package ru.korbit.ceserver.dto;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.*;
import ru.korbit.cecommon.exeptions.BadRequest;
import ru.korbit.cecommon.utility.EventActionHelper;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
    public ResponseEventFactory(ShowtimeDao showtimeDao) {
        this.showtimeDao = showtimeDao;
    }

    public REvent getResponseEvent(Event event, City city, ZonedDateTime now) {
        if (event instanceof CinemaEvent) {
            return getRCinemaEvent((CinemaEvent) event, city, now);
        }
        if (event instanceof RecurringEvent) {
            return getRRecurringEvent((RecurringEvent) event, city, now);
        }
        throw new RuntimeException("Not exist response for event = " + event.toString());
    }

    private RCinemaEvent getRCinemaEvent(CinemaEvent cinemaEvent, City city, ZonedDateTime now) {
        val priceRange = new PriceRange();
        List<RCinema> cinemas = city.getCinemas()
                .stream()
                .map(cinema -> {
                    List<RHall> halls = cinema.getHalls()
                            .parallelStream()
                            .map(hall -> {
                                val showtimes = new ArrayList<RShowtime>();
                                showtimeDao.getByHallAndEventOnDay(cinemaEvent.getId(), hall.getId(), now)
                                        .forEach(showtime -> {
                                            if (showtime.getStartTime().isBefore(now)) {
                                                return;
                                            }

                                            showtimes.add(new RShowtime(showtime, city.getZoneOffset()));

                                            if (showtime.getPriceMin() < priceRange.min) {
                                                priceRange.min = showtime.getPriceMin();
                                            }
                                            if (showtime.getPriceMax() > priceRange.max) {
                                                priceRange.max = showtime.getPriceMax();
                                            }
                                        });
                                return new RHall(hall, showtimes);
                            })
                            .filter(hall -> !hall.getShowtimes().isEmpty())
                            .collect(Collectors.toList());
                    return new RCinema(cinema, halls);
                })
                .filter(cinema -> !cinema.getHalls().isEmpty())
                .collect(Collectors.toList());

        return new RCinemaEvent(cinemaEvent, cinemas, priceRange.min, priceRange.max);
    }

    private RRecurringEvent getRRecurringEvent(RecurringEvent recurringEvent, City city, ZonedDateTime now) {
        val eventActionHelper = new EventActionHelper(city.getId(), now, now.truncatedTo(ChronoUnit.DAYS).plusDays(1));
        val time = eventActionHelper.getSetActiveDays(recurringEvent)
                .findFirst()
                .orElseThrow(() -> new BadRequest("Haven't actions today"));
        val duration = recurringEvent.getActionSchedules()
                .stream()
                .filter(actionSchedule -> actionSchedule.getCity().getId().equals(city.getId()))
                .findFirst()
                .map(ActionSchedule::getDuration)
                .orElseThrow(() -> new BadRequest("Event haven't schedule"));

        return new RRecurringEvent(recurringEvent, time, duration);
    }

    private class PriceRange {
        Integer min = Integer.MAX_VALUE;
        Integer max = Integer.MIN_VALUE;
    }
}
