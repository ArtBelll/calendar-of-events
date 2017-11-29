package ru.korbit.ceadmin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.korbit.cecommon.domain.City;

@Data
@NoArgsConstructor
public class CityDto {
    private Long id;
    private String name;
    private int zoneOffset;

    public CityDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.zoneOffset = city.getZoneOffset().getTotalSeconds();
    }
}
