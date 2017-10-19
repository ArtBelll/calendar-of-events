package ru.korbit.ceserver.controllers.v1;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.utility.DateTimeUtils;
import ru.korbit.ceserver.RangeDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 18.10.17.
 */
@RestController
@RequestMapping(value = "cities/{cityId}/events/")
@Transactional
public class EventsController extends BaseController {

    private final EventDao eventDao;

    @Autowired
    public EventsController(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @PostMapping(value = "exist-for-days")
    public ResponseEntity<?> getDaysWithEvents(@PathVariable Long cityId,
                                               @RequestParam("start_date") Long startDate,
                                               @RequestParam("finish_date") Long finishDate) {

        if (startDate == null || finishDate == null || startDate > finishDate) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        val start = Instant.ofEpochSecond(startDate).atZone(ZoneId.systemDefault()).toLocalDate();
        val finish = Instant.ofEpochSecond(finishDate).atZone(ZoneId.systemDefault()).toLocalDate();

        val activeDays = new ArrayList<RangeDate>();

        eventDao.getEventsByDateRangeAtCity(start, finish, cityId)
                .forEach(event -> {
                    val endRange = event.getFinishDay().isBefore(finish) ? event.getFinishDay() : finish;

                    if (activeDays.isEmpty()) {
                        activeDays.add(new RangeDate(event.getStartDay(), endRange));
                        return;
                    }
                    val index = activeDays.size() - 1;
                    val lastRange = activeDays.get(index);

                    if (DateTimeUtils.isBeforeOrEqual(event.getStartDay(), endRange)) {
                        if (lastRange.getFinish().isBefore(event.getFinishDay())) {
                            lastRange.setFinish(endRange);
                        }
                    } else {
                        activeDays.add(new RangeDate(event.getStartDay(), endRange));
                    }
                });

        val activeDaysLong = activeDays
                .stream()
                .flatMap(rangeDate -> getListOfDay(rangeDate.getStart(), rangeDate.getFinish()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(getResponseBody(activeDaysLong), HttpStatus.OK);
    }

    private Stream<Long> getListOfDay(LocalDate start, LocalDate finish) {
        val days = ChronoUnit.DAYS.between(start, finish);
        return LongStream.range(0, days)
                .boxed()
                .map(i -> start.plusDays(i).toEpochDay() * 24 * 60 * 60);
    }
}
