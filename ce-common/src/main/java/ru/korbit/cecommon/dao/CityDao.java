package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface CityDao extends GenericDao<City> {

    Optional<City> getByName(String name);

    Stream<City> getAll();
}
