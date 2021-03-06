package ru.korbit.ceadmin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korbit.cecommon.domain.RecurringEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RecurringEventDto {
    private Long id;
    private String title;
    private String description;
    private String additionally;
    private Integer price;
    private Float latitude;
    private Float longitude;
    private String address;
    private String place;
    private Set<EventTypeDto> eventTypes;
    private Set<ActionScheduleDto> actionSchedules = new HashSet<>();

    public RecurringEventDto(RecurringEvent recurringEvent) {
        this.id = recurringEvent.getId();
        this.title = recurringEvent.getTitle();
        this.description = recurringEvent.getDescription();
        this.additionally = recurringEvent.getAdditionally();
        this.price = recurringEvent.getPrice();
        this.latitude = recurringEvent.getLatitude();
        this.longitude = recurringEvent.getLongitude();
        this.address = recurringEvent.getAddress();
        this.place = recurringEvent.getPlace();
        this.eventTypes = recurringEvent.getEventTypes()
                .stream()
                .map(EventTypeDto::new)
                .collect(Collectors.toSet());
        this.actionSchedules = recurringEvent.getActionSchedules()
                .stream()
                .map(ActionScheduleDto::new)
                .collect(Collectors.toSet());
    }
}
