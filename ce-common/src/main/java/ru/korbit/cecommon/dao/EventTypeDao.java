package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public interface EventTypeDao extends GenericDao<EventType> {

    Optional<EventType> getByName(@NonNull String name);

    Stream<EventType> getAtCity(@NonNull Long id);
}
