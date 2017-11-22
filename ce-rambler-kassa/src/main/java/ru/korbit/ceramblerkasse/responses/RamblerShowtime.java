package ru.korbit.ceramblerkasse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.val;
import ru.korbit.cecommon.domain.Showtime;
import ru.korbit.cecommon.domain.Hall;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerShowtime {

    @JsonProperty("SessionID")
    private long showtimeRamblerId;

    @JsonProperty("CreationObjectID")
    private Integer eventId;

    @JsonProperty("Format")
    private String format;

    @JsonProperty("DateTime")
    private String startTime;

    @JsonProperty("MinPrice")
    private Integer priceMin;

    @JsonProperty("MaxPrice")
    private Integer priceMax;

    @JsonProperty("PlaceObjectID")
    private Integer placeId;

    @JsonProperty("HallID")
    private String hallRamblerId;

    @JsonProperty("HallName")
    private String hallName;

    public Hall toDBHall() {
        return new Hall(hallName, hallRamblerId);
    }

    public Showtime toDbShowtime(ZoneOffset offset) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        val startTime = ZonedDateTime.of(LocalDateTime.parse(this.startTime, formatter), offset);
        return new Showtime(format, startTime, priceMin, priceMax, showtimeRamblerId);
    }
}
