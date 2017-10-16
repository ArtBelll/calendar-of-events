package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.korbit.cecommon.domain.Hall;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerShowtime {

    @JsonProperty("SessionID")
    private long showtimeRamblerId;

    @JsonProperty("Format")
    private String format;

    @JsonProperty("DateTime")
    private String startTime;

    @JsonProperty("MinPrice")
    private int price;

    @JsonProperty("HallID")
    private int hallRamblerId;

    @JsonProperty("HallName")
    private String hallName;

    public Hall toDBHall() {
        return new Hall(hallName);
    }
}
