package ru.korbit.cecommon.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.HallDao;
import ru.korbit.cecommon.domain.Hall;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

/**
 * Created by Artur Belogur on 08.11.17.
 */
@Service
public class HallStoreHelper extends BaseStoreHelper<Hall> implements CommonStoreHelper<Hall> {

    private final HallDao hallDao;

    @Autowired
    public HallStoreHelper(HallDao hallDao) {
        super(Hall.class);
        this.hallDao = hallDao;
    }

    @Override
    public Optional<Hall> searchFromDb(Hall hall) {
        return hallDao.getByRamblerId(hall.getRamblerId());
    }
}
