package ru.korbit.ceramblerkasse.services.filters;

import javafx.util.Pair;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.Hall;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface CheckerExist {

    Pair<RamblerCity, Boolean> checkAndSave(RamblerCity city);

    Pair<RamblerCinema, Boolean> checkAndSave(RamblerCinema cinema);

    Pair<Hall, Boolean> checkAndSave(Hall hall);

    Pair<RamblerShowtime, Boolean> checkAndSave(RamblerShowtime showtime);
}
