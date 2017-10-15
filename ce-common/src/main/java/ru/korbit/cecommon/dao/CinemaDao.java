package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Cinema;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public interface CinemaDao {

    long addCinema(@NonNull Cinema cinema);

    void delete(@NonNull Cinema id);
}
