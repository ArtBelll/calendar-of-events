package ru.korbit.cecommon.services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.packet.BaseUrlRestTemplate;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Artur Belogur on 13.11.17.
 */
@Repository(value = "timeZone")
@Slf4j
public class TimeZoneDb implements TimeZone {

    private final static String LATITUDE_PARAM = "lat";
    private final static String LONGITUDE_PARAM = "lng";

    private final static String API_KEY = "5JBDOS2MO5J3";
    private final static String OFFSET_ZONE_KEY = "gmtOffset";

    private final static String BASE_PARAMS =
            String.format("key=%s&format=json&by=position&country=RU&fields=%s", API_KEY, OFFSET_ZONE_KEY);

    private final static String GET_TIME_ZONE_URL = String.format("/get-time-zone?%s={%s}&%s={%s}",
            LATITUDE_PARAM, LATITUDE_PARAM, LONGITUDE_PARAM, LONGITUDE_PARAM) + "&" + BASE_PARAMS;

    private BaseUrlRestTemplate restTemplate;

    public TimeZoneDb() {
        restTemplate = new BaseUrlRestTemplate("http://api.timezonedb.com/v2");
    }

    @Override
    public ZoneOffset getZoneOffsetByLatlng(Float lat, Float lng) {
        val params = new HashMap<String, Float>();
        params.put(LATITUDE_PARAM, lat);
        params.put(LONGITUDE_PARAM, lng);

        return Optional.ofNullable(restTemplate
                .getForObject(GET_TIME_ZONE_URL, JsonNode.class, params)
                .get(OFFSET_ZONE_KEY))
                .map(offset -> ZoneOffset.ofTotalSeconds(offset.asInt()))
                .orElseThrow(() -> {
                    log.error("Bad params lat = {} and lng ={}", lat, lng);
                    return new NullPointerException();
                });
    }
}
