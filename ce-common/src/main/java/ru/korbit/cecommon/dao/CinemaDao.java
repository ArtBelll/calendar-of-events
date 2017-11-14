package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Cinema;

import java.util.Optional;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public interface CinemaDao extends GenericDao<Cinema> {

    Optional<Cinema> getByName(String name);
}
