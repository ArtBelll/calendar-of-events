package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.City;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public interface CityDao {

    Optional<City> getCity(@NonNull Long id);

    Stream<City> getAllCity();
}
