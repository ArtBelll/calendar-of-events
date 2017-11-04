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
public class CityDaoImpl extends SessionFactoryHolder<City> implements CityDao {

    CityDaoImpl() {
        super(City.class);
    }

    @Override
    public Optional<City> getByName(String name) {
        return getSession()
                .createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public Stream<City> getAll() {
        return getSession()
                .createQuery("SELECT c FROM City c", City.class)
                .stream();
    }
}
