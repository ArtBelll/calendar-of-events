package ru.korbit.ceadmin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korbit.cecommon.domain.ActionSchedule;

@Data
@NoArgsConstructor
public class ActionScheduleDto {
    private Long id;
    private String cron;
    private Long duration;
    private CityDto city;

    public ActionScheduleDto(ActionSchedule actionSchedule) {
        this.id = actionSchedule.getId();
        this.cron = actionSchedule.getCron();
        this.duration = actionSchedule.getDuration().toMillis() / 1000;
        this.city = new CityDto(actionSchedule.getCity());
    }
}
