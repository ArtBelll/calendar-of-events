package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.Cinema;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerCinema {

    @JsonProperty("ObjectID")
    private Integer cinemaRamblerId;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Latitude")
    private Float latitude;

    @JsonProperty("Longitude")
    private Float longitude;

    @JsonProperty("Category")
    private String category;

    public Cinema toBDCinema() {
        return new Cinema(name, address, latitude, longitude);
    }
}
