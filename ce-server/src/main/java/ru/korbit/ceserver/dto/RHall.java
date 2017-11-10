package ru.korbit.ceserver.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Hall;

import java.util.List;

/**
 * Created by Artur Belogur on 10.11.17.
 */
@Data
public class RHall {

    private String name;

    private List<RShowtime> showtimes;

    public RHall(Hall hall, List<RShowtime> showtimes) {
        this.name = hall.getName();
        this.showtimes = showtimes;
    }
}
