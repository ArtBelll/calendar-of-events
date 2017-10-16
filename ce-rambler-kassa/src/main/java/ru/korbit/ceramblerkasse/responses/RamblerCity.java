package ru.korbit.ceramblerkasse.responses;

import lombok.Data;
import ru.korbit.cecommon.domain.City;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerCity {
    private int cityRamblerId;
    private String name;

    public City toDBCity() {
        return new City(name);
    }
}
