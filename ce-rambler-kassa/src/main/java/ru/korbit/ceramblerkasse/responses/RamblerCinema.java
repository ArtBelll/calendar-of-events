package ru.korbit.ceramblerkasse.responses;

import lombok.Data;
import ru.korbit.cecommon.domain.Cinema;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerCinema {
    private int cinemaRamblerId;
    private String address;
    private String name;
    private Float latitude;
    private Float longitude;

    public Cinema toBDCinema() {
        return new Cinema(name, address, latitude, longitude);
    }
}
