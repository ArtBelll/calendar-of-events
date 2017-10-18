package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.val;
import ru.korbit.cecommon.domain.Showtime;
import ru.korbit.cecommon.domain.Hall;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @JsonProperty("PlaceObjectID")
    private Integer placeId;

    @JsonProperty("HallID")
    private String hallRamblerId;

    @JsonProperty("HallName")
    private String hallName;

    public Hall toDBHall() {
        return new Hall(hallName, hallRamblerId);
    }

    public Showtime toDbShowtime() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        val startTime = LocalDateTime.parse(this.startTime, formatter);
        return new Showtime(format, startTime, price, showtimeRamblerId);
    }
}
