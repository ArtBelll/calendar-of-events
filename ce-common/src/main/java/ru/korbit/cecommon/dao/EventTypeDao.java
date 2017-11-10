package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public interface EventTypeDao extends GenericDao<EventType> {

    Optional<EventType> getByName(String name);

    Stream<EventType> getAtCity(Long id);
}
