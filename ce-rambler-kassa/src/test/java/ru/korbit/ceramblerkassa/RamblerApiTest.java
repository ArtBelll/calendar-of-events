package ru.korbit.ceramblerkassa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.korbit.ceramblerkasse.Environment;
import ru.korbit.ceramblerkasse.services.impl.RamblerKassa;
import ru.korbit.ceramblerkasse.services.RamblerKassaApi;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerEvent;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Artur Belogur on 16.10.17.
 */
public class RamblerApiTest {

    private final Integer KALININGRAD_ID = 1700;
    private final LocalDate NEXT_WEEK = LocalDate.now().plusWeeks(1);

    private RamblerKassaApi ramblerKassa;

    @Before
    public void setUpHost() {
        Environment.host = Environment.PROXY;
        ramblerKassa = new RamblerKassa();
    }

    @Test
    public void getCitiesTest() {
        List<RamblerCity> cities = ramblerKassa.getCities();
        Assert.assertTrue(cities.size() > 0 && cities.get(0).getClass() == RamblerCity.class);
    }

    @Test
    public void getCinamesAtCityTest() {
        List<RamblerCinema> cinemas = ramblerKassa.getCinemasAtCity(KALININGRAD_ID);
        Assert.assertTrue(cinemas.size() > 0 && cinemas.get(0).getClass() == RamblerCinema.class);
    }

    @Test
    public void getEventsLessDateAtCityTest() {
        List<RamblerEvent> events = ramblerKassa.getEventsLessDateAtCity(KALININGRAD_ID, NEXT_WEEK);
        Assert.assertTrue(events.size() > 0 && events.get(0).getClass() == RamblerEvent.class);
    }

    @Test
    public void getShowtimesEventLessDateAtCity() {
        List<RamblerEvent> events = ramblerKassa.getEventsLessDateAtCity(KALININGRAD_ID, NEXT_WEEK);
        Assert.assertTrue(events.size() > 0 && events.get(0).getClass() == RamblerEvent.class);

        List<RamblerShowtime> showtimes = ramblerKassa.getShowtimesEventLessDateAtCity(KALININGRAD_ID,
                NEXT_WEEK, events.get(0).getEventRamblerId());
        Assert.assertTrue(showtimes.size() > 0 && showtimes.get(0).getClass() == RamblerShowtime.class);
    }
}
