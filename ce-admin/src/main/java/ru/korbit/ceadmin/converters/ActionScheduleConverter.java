package ru.korbit.ceadmin.converters;

import ru.korbit.ceadmin.dto.ActionScheduleDto;
import ru.korbit.cecommon.domain.ActionSchedule;
import ru.korbit.cecommon.packet.Converter;

import java.time.Duration;

public class ActionScheduleConverter extends Converter<ActionScheduleDto, ActionSchedule> {

    public ActionScheduleConverter() {
        super(actionScheduleDto -> {
            Duration duration = Duration.ofSeconds(actionScheduleDto.getDuration());
            return new ActionSchedule(actionScheduleDto.getCron(), duration);
        }, ActionScheduleDto::new);
    }
}
