package ru.korbit.ceserver.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.EventType;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class REventType {

    private Long id;

    private String name;

    public REventType(EventType eventType) {
        this.id = eventType.getId();
        this.name = eventType.getName();
    }
}
