package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.domain.City;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
@Repository(value = "cityDao")
public class CityDaoImpl extends SessionFactoryHolder implements CityDao {

    @Override
    public long addCity(City city) {
        super.save(city);
        return city.getId();
    }

    @Override
    public Optional<City> getCity(Long id) {
        return getSession().byId(City.class).loadOptional(id);
    }

    @Override
    public Stream<City> getAllCity() {
        return getSession()
                .createQuery("SELECT c FROM City c", City.class)
                .stream();
    }
}
