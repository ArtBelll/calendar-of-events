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
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    CityDao cityDao;

    @Test
    public void addGetTest() {
        val simpleEvent = getSimpleEvent(TITLE, PLACE);
        val eventIn = eventDao.save(simpleEvent);
        val eventOut = eventDao.get(eventIn.getId());
        Assert.assertTrue(eventOut.isPresent());
        Assert.assertTrue(Objects.equals(eventOut.get().getTitle(), TITLE));
    }

    @Test
    public void getByDateRange() {
        val requestStartDate = LocalDate.parse("2017-10-01");
        val requestFinishDate = LocalDate.parse("2017-10-08");

        eventDao.save(getSimpleEvent(TITLE, PLACE));
        val events = eventDao.getByDateRangeAtCity(requestStartDate, requestFinishDate, 1L, new ArrayList<>());
        events.forEach(event -> {
            Assert.assertTrue(event.getStartDay().isBefore(requestFinishDate)
                    && event.getFinishDay().isAfter(requestStartDate));
        });
    }

    @Test
    public void searchEvent() {
        val city = cityDao.save(new City("TestCity"));
        Cinema cinema = new Cinema();
        cinema.setPlace(PLACE);
        cinema.setName(PLACE);
        List<Cinema> cinemas = new ArrayList<>();
        cinema.setCity(city);
        cinemas.add(cinema);
        cinemaDao.save(cinema);
        city.setCinemas(cinemas);

        val events = new ArrayList<Event>();

        events.add(eventDao.save(getSimpleEvent(TITLE, PLACE)));
        events.add(eventDao.save(getSimpleEvent("1", PLACE)));
        events.add(eventDao.save(getSimpleEvent("2", PLACE)));

        events.add(eventDao.save(getCinemaEvent("3", PLACE)));
        events.add(eventDao.save(getCinemaEvent(TITLE, "p1")));
        events.add(eventDao.save(getCinemaEvent("4", "p2")));

        city.setEvents(events);
        cityDao.update(city);
        events.forEach(event -> {
            event.setCity(city);
            eventDao.update(event);
        });

        val eventsByPlace = eventDao.searchEvents("", PLACE, LocalDate.now(), city.getId()).collect(Collectors.toList());
        val eventsByTitle = eventDao.searchEvents(TITLE, "", LocalDate.now(), city.getId()).collect(Collectors.toList());
        val eventsByTitleAndPlace = eventDao.searchEvents(TITLE, PLACE, LocalDate.now(), city.getId()).collect(Collectors.toList());

        Assert.assertTrue(eventsByPlace.size() == 6);
        Assert.assertTrue(eventsByTitle.size() == 2);
        Assert.assertTrue(eventsByTitleAndPlace.size() == 2);
    }

    private SimpleEvent getSimpleEvent(String title, String place) {
        SimpleEvent simpleEvent = new SimpleEvent();


        simpleEvent.setTitle(title);
        simpleEvent.setPlace(place);

        simpleEvent.setStartDay(LocalDate.now().minusDays(1));
        simpleEvent.setFinishDay(LocalDate.now().plusDays(1));

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

        cinemaEvent.setStartDay(LocalDate.now().minusDays(1));
        cinemaEvent.setFinishDay(LocalDate.now().plusDays(1));

        List<EventSchedule> eventScheduleList = new ArrayList<>();
        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartTime(LocalTime.parse("10:00"));
        eventSchedule.setStartTime(LocalTime.parse("16:00"));
        eventScheduleList.add(eventSchedule);

        cinemaEvent.setEventSchedules(eventScheduleList);

        return cinemaEvent;
    }
}
