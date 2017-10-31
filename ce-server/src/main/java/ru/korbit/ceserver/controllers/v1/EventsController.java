package ru.korbit.ceserver.controllers.v1;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.utility.DateTimeUtils;
import ru.korbit.ceserver.DateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    @GetMapping(value = "exist-for-days")
    public ResponseEntity<?> getDaysWithEvents(@PathVariable Long cityId,
                                               @RequestParam("start_date") Long beginRange,
                                               @RequestParam("finish_date") Long endDateRange) {

        if (beginRange == null || endDateRange == null || beginRange > endDateRange) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        val start = DateTimeUtils.epochSecondToLocalDate(beginRange);
        val finish = DateTimeUtils.epochSecondToLocalDate(endDateRange);

        val activeDaysLong = getActiveDateRanges(eventDao.getEventsByDateRangeAtCity(start, finish, cityId), finish)
                .stream()
                .flatMap(rangeDate -> DateTimeUtils.getListOfDayInSecondsBetween(rangeDate.getStart(), rangeDate.getFinish()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(getResponseBody(activeDaysLong), HttpStatus.OK);
    }

    private List<DateRange> getActiveDateRanges(Stream<Event> events, LocalDate endRange) {
        val activeDaysInDataRanges = new ArrayList<DateRange>();
        events.forEach(event -> {
                    val finish = event.getFinishDay().isBefore(endRange) ? event.getFinishDay() : endRange;

                    if (activeDaysInDataRanges.isEmpty()) {
                        activeDaysInDataRanges.add(new DateRange(event.getStartDay(), finish));
                        return;
                    }
                    val index = activeDaysInDataRanges.size() - 1;
                    val lastRange = activeDaysInDataRanges.get(index);

                    if (DateTimeUtils.isBeforeOrEqual(event.getStartDay(), finish)) {
                        if (lastRange.getFinish().isBefore(event.getFinishDay())) {
                            lastRange.setFinish(finish);
                        }
                    } else {
                        activeDaysInDataRanges.add(new DateRange(event.getStartDay(), finish));
                    }
                });

        return activeDaysInDataRanges;

    }
}
