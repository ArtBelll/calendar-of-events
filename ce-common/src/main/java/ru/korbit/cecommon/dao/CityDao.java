package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface CityDao {

    long addCity(@NonNull City city);

    Optional<City> getCity(@NonNull Long id);

    Optional<City> getCityByName(@NonNull String name);

    Stream<City> getAllCity();

    Stream<EventType> getEventTypesAtCity(@NonNull Long id);
}
