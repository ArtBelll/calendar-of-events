package ru.korbit.ceserver;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.domain.CinemaEvent;
import ru.korbit.cecommon.domain.EventSchedule;
import ru.korbit.cecommon.domain.SimpleEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Artur Belogur on 12.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@Transactional
public class EventDaoTest {

    private final String TITLE = "SimpleEvent";
    private final String PLACE = "Awesome Place";

    @Autowired
    EventDao eventDao;

    @Autowired
    CinemaDao cinemaDao;

    @Test
    public void addGetTest() {
        val simpleEvent = getSimpleEvent(TITLE, PLACE);
        val id = eventDao.addEvent(simpleEvent);
        val event = eventDao.getEventById(id);
        Assert.assertTrue(event.isPresent());
        Assert.assertTrue(Objects.equals(event.get().getTitle(), TITLE));
    }

    @Test
    public void getByDateRange() {
        val requestStartDate = LocalDate.parse("2017-10-01");
        val requestFinishDate = LocalDate.parse("2017-10-08");

        eventDao.addEvent(getSimpleEvent(TITLE, PLACE));
        val events = eventDao.getEventsByDateRange(requestStartDate, requestFinishDate);
        events.forEach(event -> {
            Assert.assertTrue(event.getStartDay().isBefore(requestFinishDate)
                    && event.getFinishDay().isAfter(requestStartDate));
        });
    }

    @Test
    public void searchEvent() {
        eventDao.addEvent(getSimpleEvent(TITLE, PLACE));
        eventDao.addEvent(getSimpleEvent("1", PLACE));
        eventDao.addEvent(getSimpleEvent("2", PLACE));

        eventDao.addEvent(getCinemaEvent("3", PLACE));
        eventDao.addEvent(getCinemaEvent(TITLE, "p1"));
        eventDao.addEvent(getCinemaEvent("4", "p2"));

        val eventsByPlace = eventDao.searchEvents("", PLACE).count();
        val eventsByTitle = eventDao.searchEvents(TITLE, "").count();
        val eventsByTitleAndPlace = eventDao.searchEvents(TITLE, PLACE).count();

        Assert.assertTrue(eventsByPlace == 4);
        Assert.assertTrue(eventsByTitle == 2);
        Assert.assertTrue(eventsByTitleAndPlace == 1);
    }

    private SimpleEvent getSimpleEvent(String title, String place) {
        SimpleEvent simpleEvent = new SimpleEvent();

        simpleEvent.setTitle(title);
        simpleEvent.setPlace(place);

        simpleEvent.setStartDay(LocalDate.parse("2017-10-02"));
        simpleEvent.setFinishDay(LocalDate.parse("2017-10-04"));

        List<EventSchedule> eventScheduleList = new ArrayList<>();
        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartTime(LocalTime.parse("10:00"));
        eventSchedule.setStartTime(LocalTime.parse("16:00"));
        eventScheduleList.add(eventSchedule);

        simpleEvent.setEventSchedules(eventScheduleList);

        return simpleEvent;
    }

    private CinemaEvent getCinemaEvent(String title, String place) {
        CinemaEvent cinemaEvent = new CinemaEvent();

        cinemaEvent.setTitle(title);

        Cinema cinema = new Cinema();
        cinema.setPlace(place);
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(cinema);
        cinemaDao.addCinema(cinema);
        cinemaEvent.setCinemas(cinemas);

        cinemaEvent.setStartDay(LocalDate.parse("2017-10-02"));
        cinemaEvent.setFinishDay(LocalDate.parse("2017-10-04"));

        List<EventSchedule> eventScheduleList = new ArrayList<>();
        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartTime(LocalTime.parse("10:00"));
        eventSchedule.setStartTime(LocalTime.parse("16:00"));
        eventScheduleList.add(eventSchedule);

        cinemaEvent.setEventSchedules(eventScheduleList);

        return cinemaEvent;
    }
}
