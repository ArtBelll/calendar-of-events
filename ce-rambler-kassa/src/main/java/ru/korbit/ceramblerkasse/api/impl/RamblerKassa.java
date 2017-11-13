package ru.korbit.ceramblerkasse.api.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.packet.BaseUrlRestTemplate;
import ru.korbit.ceramblerkasse.Environment;
import ru.korbit.ceramblerkasse.api.RamblerKassaApi;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerEvent;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Slf4j
@Repository(value = "ramblerKassaApi")
public class RamblerKassa implements RamblerKassaApi {

    private final static String OBJECT_ID_PARAM = "ObjectID";
    private final static String CITY_ID_PARAM = "CityID";
    private final static String MAX_DATE_PARAM = "MaxDate";
    private final static String DATE_TO = "DateTo";

    private final static String CITIES_URL = "/cities";
    private final static String PLACE_URL = String.format("/place/object?%s={%s}",
            OBJECT_ID_PARAM, OBJECT_ID_PARAM) ;
    private final static String PLACE_LIST_URL = String.format("/place/list?%s={%s}", CITY_ID_PARAM, CITY_ID_PARAM);
    private final static String EVENTS_URL = String.format("/movie/list?%s={%s}&%s={%s}",
            CITY_ID_PARAM, CITY_ID_PARAM, MAX_DATE_PARAM, MAX_DATE_PARAM);
     private final static String CITY_SCHEDULE_URL = String.format("/movie/schedule?%s={%s}&%s={%s}",
            CITY_ID_PARAM, CITY_ID_PARAM, DATE_TO, DATE_TO);

    private final ObjectMapper mapper;

    private BaseUrlRestTemplate restTemplate;

    public RamblerKassa() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        restTemplate = new BaseUrlRestTemplate(Environment.getBaseUrl());
    }

    @Override
    public List<RamblerCity> getCities() {
        val jsonNodeRoot = restTemplate.getForObject(CITIES_URL, JsonNode.class);
        return resultList(jsonNodeRoot, RamblerCity.class);
    }

    @Override
    public RamblerCinema getCinema(Integer cinemaRamblerId) {
        val params = new HashMap<String, Object>();
        params.put(OBJECT_ID_PARAM, cinemaRamblerId);
        return restTemplate.getForObject(PLACE_URL, RamblerCinema.class, params);
    }

    @Override
    public List<RamblerCinema> getCinemasAtCity(Integer cityRamblerId) {
        val params = new HashMap<String, Object>();
        params.put(CITY_ID_PARAM, cityRamblerId);
        val jsonNodeRoot = restTemplate.getForObject(PLACE_LIST_URL, JsonNode.class, params);
        return resultList(jsonNodeRoot, RamblerCinema.class)
                .stream()
                .filter(this::categoryIsCinema)
                .collect(Collectors.toList());
    }

    @Override
    public List<RamblerEvent> getEventsLessDateAtCity(Integer cityRamblerId, ZonedDateTime maxDate) {
        val params = new HashMap<String, Object>();
        params.put(CITY_ID_PARAM, cityRamblerId);
        params.put(MAX_DATE_PARAM, maxDate);
        val jsonNodeRoot = restTemplate.getForObject(EVENTS_URL, JsonNode.class, params);

        return resultList(jsonNodeRoot, RamblerEvent.class);
    }

    @Override
    public List<RamblerShowtime> getShowtimesCityLessDate(Integer cityRamblerId, ZonedDateTime maxDate) {
        val params = new HashMap<String, Object>();
        params.put(CITY_ID_PARAM, cityRamblerId);
        params.put(DATE_TO, maxDate);
        val jsonNodeRoot = restTemplate.getForObject(CITY_SCHEDULE_URL, JsonNode.class, params);

        return resultList(jsonNodeRoot, RamblerShowtime.class);
    }

    private <T> List<T> resultList(JsonNode jsonNode, Class<T> classType) {
        try {
            return mapper.reader().withRootName("List")
                    .readValue(jsonNode.traverse(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, classType));
        } catch (IOException e) {
            log.error("Response body = {}", jsonNode);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private boolean categoryIsCinema(RamblerCinema event) {
        return event.getCategory().equals("Cinema");
    }
}
