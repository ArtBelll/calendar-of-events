package ru.korbit.ceramblerkasse;

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

    List<RamblerCinema> getCinemasAtCity(@NonNull int cityRamblerId);

    List<RamblerEvent> getEventsLessDateAtCity(@NonNull Long cityRamblerId, @NonNull LocalDate maxDate);

    List<RamblerShowtime> getShowtimesEventLessDateAtCity(@NonNull Long cityRamblerId,
                                                          @NonNull LocalDate maxDate,
                                                          @NonNull int showtimeRamblerId);
}
