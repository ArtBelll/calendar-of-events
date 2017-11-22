package ru.korbit.ceserver.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Cinema;

import java.util.List;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class RCinema {

    private String name;

    private String address;

    private Float latitude;

    private Float longitude;

    private List<RHall> halls;

    public RCinema(Cinema cinema, List<RHall> halls) {
        this.name = cinema.getName();
        this.address = cinema.getAddress();
        this.latitude = cinema.getLatitude();
        this.longitude = cinema.getLongitude();
        this.halls = halls;
    }
}
