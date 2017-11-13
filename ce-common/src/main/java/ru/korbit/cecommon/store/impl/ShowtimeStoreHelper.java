package ru.korbit.cecommon.store.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.domain.CinemaEventHall;
import ru.korbit.cecommon.domain.Showtime;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

/**
 * Created by Artur Belogur on 08.11.17.
 */
@Service
public class ShowtimeStoreHelper extends BaseStoreHelper<Showtime>
        implements CommonStoreHelper<Showtime> {

    private final ShowtimeDao showtimeDao;


    public ShowtimeStoreHelper(ShowtimeDao showtimeDao) {
        super(Showtime.class);
        this.showtimeDao = showtimeDao;
    }

    @Override
    public Optional<Showtime> searchFromDb(Showtime showtime) {
        val id = new CinemaEventHall(showtime.getCinemaEvent().getId(),
                showtime.getHall().getId(), showtime.getRamblerId());
        return showtimeDao.get(id);
    }
}
