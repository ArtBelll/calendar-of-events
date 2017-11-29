package ru.korbit.ceadmin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korbit.cecommon.domain.EventType;

@Data
@NoArgsConstructor
public class EventTypeDto {
    private Long id;
    private String name;

    public EventTypeDto(EventType eventType) {
        this.id = eventType.getId();
        this.name = eventType.getName();
    }
}
