package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.CinemaEventHallShowtime;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CinemaEventHallShowtimeDao {

    void addCinemaEventHallShowtime(@NonNull CinemaEventHallShowtime cinemaEventHallShowtime);

    Optional<CinemaEventHallShowtime> getCinemaEventHallShowtime(@NonNull Long id);

    Optional<CinemaEventHallShowtime> getCinemaEventHallShowtimeByRamblerId(@NonNull Integer id);
}
