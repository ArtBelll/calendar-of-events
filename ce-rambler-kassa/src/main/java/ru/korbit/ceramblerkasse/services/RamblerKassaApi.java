package ru.korbit.ceramblerkasse.services;

import lombok.NonNull;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerEvent;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Artur Belogur on 15.10.17.
 */
public interface RamblerKassaApi {

    List<RamblerCity> getCities();

    List<RamblerCinema> getCinemasAtCity(@NonNull Integer cityRamblerId);

    List<RamblerEvent> getEventsLessDateAtCity(@NonNull Integer cityRamblerId, @NonNull LocalDate maxDate);

    List<RamblerShowtime> getShowtimesEventLessDateAtCity(@NonNull Integer cityRamblerId,
                                                          @NonNull LocalDate maxDate,
                                                          @NonNull Integer eventRamblerId);
}
