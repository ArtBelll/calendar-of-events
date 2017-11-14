package ru.korbit.ceserver.controllers.v1;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.exeptions.BadRequest;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@Slf4j
public abstract class BaseController {

    private final String DATA_KEY = "data";
    private final String TYPE_KEY = "type";

    @Autowired
    private CityDao cityDao;

    Map<String, Object> getResponseBody(Object object) {
        val body = new HashMap<String, Object>();
        body.put(DATA_KEY, object);
        return body;
    }

    Map<String, Object> getResponseBody(int type, Object object) {
        val body = new HashMap<String, Object>();
        body.put(TYPE_KEY, type);
        body.put(DATA_KEY, object);
        return body;
    }

    ZoneOffset getCityZone(Long cityId) {
        return cityDao.get(cityId)
                .orElseThrow(() -> new BadRequest("City not exist"))
                .getZoneOffset();
    }

    void checkRequestDateRange(Long begin, Long end, ZoneOffset zoneOffset) {
        if (begin == null || end == null) {
            throw new BadRequest("Incorrect time period");
        }

        if (begin > end) {
            log.info("Bad request: begin range = {}, end range = {}",
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(begin), zoneOffset),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(end), zoneOffset));
            throw new BadRequest("Bad date range: begin = " + begin + " end = " + end);
        }
    }
}
