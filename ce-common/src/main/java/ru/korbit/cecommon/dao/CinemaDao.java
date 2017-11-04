package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Cinema;

import java.util.Optional;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public interface CinemaDao extends GenericDao<Cinema> {

    Cinema save(@NonNull Cinema cinema);

    Optional<Cinema> getByName(@NonNull String name);
}
