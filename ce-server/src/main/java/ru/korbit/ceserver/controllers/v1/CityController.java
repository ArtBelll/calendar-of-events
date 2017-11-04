package ru.korbit.ceserver.controllers.v1;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.dao.EventTypeDao;

import java.util.stream.Collectors;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@RestController
@Transactional
@RequestMapping(value = "cities")
@Slf4j
public class CityController extends BaseController {

    private final CityDao cityDao;
    private final EventTypeDao eventTypeDao;

    public CityController(CityDao cityDao, EventTypeDao eventTypeDao) {
        this.cityDao = cityDao;
        this.eventTypeDao = eventTypeDao;
    }

    @GetMapping
    public ResponseEntity<?> getCities() {
        val cities = cityDao.getAll().collect(Collectors.toList());
        return new ResponseEntity<>(getResponseBody(cities), HttpStatus.OK);
    }

    @GetMapping(value = "/{cityId}/types")
    public ResponseEntity<?> getTypesInCity(@PathVariable("cityId") Long cityId) {
        val types = eventTypeDao.getAtCity(cityId).collect(Collectors.toList());
        return new ResponseEntity<>(getResponseBody(types), HttpStatus.OK);
    }
}
