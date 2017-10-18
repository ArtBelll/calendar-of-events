package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.CinemaEventHall;
import ru.korbit.cecommon.domain.Showtime;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface ShowtimeDao {

    void addShowtime(@NonNull Showtime showtime);

    Optional<Showtime> getShowtime(@NonNull CinemaEventHall id);

    Optional<Showtime> getShowtimeByRamblerId(@NonNull Long ramblerId);
}
