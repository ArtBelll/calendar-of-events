package ru.korbit.cecommon.store.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.store.CommonStoreHelper;

import java.util.Optional;

/**
 * Created by Artur Belogur on 07.11.17.
 */
@Service
public class CityStoreHelper extends BaseStoreHelper<City> implements CommonStoreHelper<City> {

    private final CityDao cityDao;

    @Autowired
    public CityStoreHelper(CityDao cityDao) {
        super(City.class);
        this.cityDao = cityDao;
    }

    @Override
    public Optional<City> searchFromDb(City city) {
        return cityDao.getByName(city.getName());
    }
}
