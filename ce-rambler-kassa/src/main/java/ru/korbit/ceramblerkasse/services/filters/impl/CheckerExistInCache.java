package ru.korbit.ceramblerkasse.services.filters.impl;

import javafx.util.Pair;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.domain.Hall;
import ru.korbit.ceramblerkasse.responses.RamblerCinema;
import ru.korbit.ceramblerkasse.responses.RamblerCity;
import ru.korbit.ceramblerkasse.responses.RamblerShowtime;
import ru.korbit.ceramblerkasse.services.filters.CheckerExist;

/**
 * Created by Artur Belogur on 17.10.17.
 */
//TODO
@Component
public class CheckerExistInCache implements CheckerExist {


    @Override
    public Pair<RamblerCity, Boolean> checkAndSave(RamblerCity city) {
        return new Pair<>(city, false);
    }

    @Override
    public Pair<RamblerCinema, Boolean> checkAndSave(RamblerCinema cinema) {
        return null;
    }

    @Override
    public Pair<Hall, Boolean> checkAndSave(Hall hall) {
        return null;
    }

    @Override
    public Pair<RamblerShowtime, Boolean> checkAndSave(RamblerShowtime showtime) {
        return null;
    }
}
