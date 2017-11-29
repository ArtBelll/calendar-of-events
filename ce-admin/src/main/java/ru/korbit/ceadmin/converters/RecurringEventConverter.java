package ru.korbit.ceadmin.converters;

import ru.korbit.ceadmin.dto.RecurringEventDto;
import ru.korbit.cecommon.domain.RecurringEvent;
import ru.korbit.cecommon.packet.Converter;

public class RecurringEventConverter extends Converter<RecurringEventDto, RecurringEvent> {

    public RecurringEventConverter() {
        super(recurringEventDto -> {
            RecurringEvent event = new RecurringEvent();
            event.setTitle(recurringEventDto.getTitle());
            event.setDescription(recurringEventDto.getDescription());
            event.setAdditionally(recurringEventDto.getAdditionally());
            event.setPrice(recurringEventDto.getPrice());
            event.setLatitude(recurringEventDto.getLatitude());
            event.setLongitude(recurringEventDto.getLongitude());
            event.setAddress(recurringEventDto.getAddress());
            event.setPlace(recurringEventDto.getPlace());
            return event;
        }, RecurringEventDto::new);
    }
}
