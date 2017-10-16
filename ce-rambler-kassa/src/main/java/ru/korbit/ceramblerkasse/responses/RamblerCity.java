package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.City;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerCity {

    @JsonProperty("CityID")
    private Integer cityRamblerId;

    @JsonProperty("Name")
    private String name;

    public City toDBCity() {
        return new City(name);
    }
}
