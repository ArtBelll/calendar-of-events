package ru.korbit.cecommon.dao;

import lombok.NonNull;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;

/**
 * Created by Artur Belogur on 19.10.17.
 */
public interface EventTypeDao {

    long addEventType(@NonNull EventType eventType);

    Optional<EventType> getEventTypeByName(@NonNull String name);
}
