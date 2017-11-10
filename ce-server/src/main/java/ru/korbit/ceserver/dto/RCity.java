package ru.korbit.ceserver.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.City;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class RCity {

    private Long id;

    private String name;

    public RCity(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }
}
