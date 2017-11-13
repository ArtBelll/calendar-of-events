package ru.korbit.ceserver.controllers.v1;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.exeptions.BadRequest;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artur Belogur on 17.10.17.
 */
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

    protected ZoneOffset getCityZone(Long cityId) {
        return cityDao.get(cityId)
                .orElseThrow(() -> new BadRequest("City not exist"))
                .getZoneOffset();
    }
}
