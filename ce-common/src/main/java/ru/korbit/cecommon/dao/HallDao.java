package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Hall;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface HallDao {

    long addHall(@NonNull Hall hall);

    Optional<Hall> getHall(@NonNull Long id);

    Optional<Hall> getHallByRamblerId(@NonNull Integer id);
}
