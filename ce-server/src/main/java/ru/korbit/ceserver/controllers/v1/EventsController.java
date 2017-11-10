package ru.korbit.ceserver.controllers.v1;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.exeptions.BadRequest;
import ru.korbit.cecommon.exeptions.NotExist;
import ru.korbit.cecommon.utility.DateTimeUtils;
import ru.korbit.ceserver.dto.RGeneralEvent;
import ru.korbit.ceserver.dto.ResponseEventFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 18.10.17.
 */
@RestController
@Transactional
@Slf4j
@RequestMapping(value = "cities/{cityId}/events")
public class EventsController extends BaseController {

    private final EventDao eventDao;

    private final ResponseEventFactory responseEventFactory;

    @Autowired
    public EventsController(EventDao eventDao, ResponseEventFactory responseEventFactory) {
        this.eventDao = eventDao;
        this.responseEventFactory = responseEventFactory;
    }

    @PostMapping(value = "exist-for-days")
    public ResponseEntity<?> getDaysWithEvents(@PathVariable Long cityId,
                                               @RequestBody List<Long> ignoreTypes,
                                               @RequestParam("start_date") Long beginRange,
                                               @RequestParam("finish_date") Long endRange) {

        if (beginRange > endRange) {
            log.info("Bad request: begin range = {}, end range = {}",
                    DateTimeUtils.epochSecondsToLocalDate(beginRange),
                    DateTimeUtils.epochSecondsToLocalDate(endRange));
            throw new BadRequest("Bad date range: begin = " + beginRange + " end = " + endRange);
        }

        val start = DateTimeUtils.epochSecondsToLocalDate(beginRange);
        val finish = DateTimeUtils.epochSecondsToLocalDate(endRange);

        val events = eventDao.getByDateRangeAtCity(start, finish, cityId, ignoreTypes);
        val activeDaysLong = DateTimeUtils
                .getActiveDateRanges(events, finish)
                .stream()
                .flatMap(rangeDate -> DateTimeUtils.getListOfDayInSecondsBetween(rangeDate.getStart(), rangeDate.getFinish()))
                .collect(Collectors.toList());

        log.info("Get active days in rage, start = {}, finish = {}, number = {}", start, finish, activeDaysLong.size());
        return new ResponseEntity<>(getResponseBody(activeDaysLong), HttpStatus.OK);
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> searchEvents(@PathVariable("cityId") Long cityId,
                                          @RequestParam(value = "title", defaultValue = "") String title,
                                          @RequestParam(value = "place", defaultValue = "") String place) {
        val events = eventDao.searchEvents(title, place, LocalDate.now(), cityId)
                .map(event -> new RGeneralEvent(event, ""))
                .collect(Collectors.toList());
        log.info("Search event: title = {}, place = {}, number = {}", title, place, events.size());
        return new ResponseEntity<>(getResponseBody(events), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getEvents(@PathVariable Long cityId,
                                       @RequestParam("start_date") Long beginRange,
                                       @RequestParam("finish_date") Long endRange) {

        if (beginRange > endRange) {
            log.info("Bad request: begin range = {}, end range = {}",
                    DateTimeUtils.epochSecondsToLocalDate(beginRange),
                    DateTimeUtils.epochSecondsToLocalDate(endRange));
            throw new BadRequest("Bad date range: begin = " + beginRange + " end = " + endRange);
        }

        val start = DateTimeUtils.epochSecondsToLocalDate(beginRange);
        val finish = DateTimeUtils.epochSecondsToLocalDate(endRange);

        ListMultimap<String, RGeneralEvent> events = MultimapBuilder.hashKeys().arrayListValues().build();

        eventDao.getByDateRangeAtCity(start, finish, cityId, new ArrayList<>())
                .forEach(event -> {
                    val generalEvent = new RGeneralEvent(event, "");
                    event.getEventTypes().forEach(eventType -> {
                        events.put(eventType.getName(), generalEvent);
                    });
                });

        log.info("Get event by date range group by types");
        return new ResponseEntity<>(getResponseBody(events), HttpStatus.OK);
    }

    @GetMapping(value = "{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable("cityId") Long cityId,
                                      @PathVariable("eventId") Long eventId) {
        return eventDao.get(eventId)
                .map(event -> responseEventFactory.getResponseEvent(event, cityId))
                .map(event -> {
                    log.info("Get event by id = {}. Event = {}", eventId, event);
                    return new ResponseEntity<>(getResponseBody(event.getType(), event), HttpStatus.OK);
                })
                .orElseThrow(() -> new NotExist("Event not found"));
    }
}
