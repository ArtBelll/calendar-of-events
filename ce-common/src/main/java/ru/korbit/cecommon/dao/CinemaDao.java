package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.Cinema;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public interface CinemaDao extends GenericDao<Cinema> {

    Cinema save(@NonNull Cinema cinema);

    Optional<Cinema> getByName(@NonNull String name);

    Stream<Cinema> getByEventOnDay(@NonNull Long eventId, @NonNull LocalDateTime dateFrom);
}
