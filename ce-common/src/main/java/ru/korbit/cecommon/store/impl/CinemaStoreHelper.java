package ru.korbit.cecommon.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

/**
 * Created by Artur Belogur on 07.11.17.
 */
@Service
public class CinemaStoreHelper extends BaseStoreHelper<Cinema> implements CommonStoreHelper<Cinema> {

    private final CinemaDao cinemaDao;

    @Autowired
    public CinemaStoreHelper(CinemaDao cinemaDao) {
        super(Cinema.class);
        this.cinemaDao = cinemaDao;
    }

    @Override
    public Optional<Cinema> searchFromDb(Cinema cinema) {
        return cinemaDao.getByName(cinema.getName());
    }
}
