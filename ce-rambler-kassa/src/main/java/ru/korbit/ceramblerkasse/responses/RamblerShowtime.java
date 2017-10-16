package ru.korbit.ceramblerkasse.responses;

import lombok.Data;
import ru.korbit.cecommon.domain.Hall;

import java.time.LocalDateTime;

/**
 * Created by Artur Belogur on 15.10.17.
 */
@Data
public class RamblerShowtime {
    private long showtimeRamblerId;
    private String format;
    private LocalDateTime startTime;
    private int price;
    private int hallRamblerId;
    private String hallName;

    public Hall toDBHall() {
        return new Hall(hallName);
    }
}
