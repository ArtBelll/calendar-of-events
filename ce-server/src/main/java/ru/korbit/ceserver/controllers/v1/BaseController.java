package ru.korbit.ceserver.controllers.v1;

import lombok.val;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public abstract class BaseController {

    private final String DATA_KEY = "data";
    private final String TYPE_KEY = "type";

    protected Map<String, Object> getResponseBody(Object object) {
        val body = new HashMap<String, Object>();
        body.put(DATA_KEY, object);
        return body;
    }

    protected Map<String, Object> getResponseBody(String type, Object object) {
        val body = new HashMap<String, Object>();
        body.put(TYPE_KEY, type);
        body.put(DATA_KEY, object);
        return body;
    }
}
