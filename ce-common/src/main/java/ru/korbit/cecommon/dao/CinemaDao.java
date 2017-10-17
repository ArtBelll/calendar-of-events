package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Cinema;

import java.util.Optional;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public interface CinemaDao {

    long addCinema(@NonNull Cinema cinema);

    Optional<Cinema> getCinema(@NonNull Long id);

    Optional<Cinema> getCinemaByName(@NonNull String name);
}
