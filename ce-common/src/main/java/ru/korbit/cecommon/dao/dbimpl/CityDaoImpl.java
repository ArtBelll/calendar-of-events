package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.EventType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
@Repository(value = "cityDao")
public class CityDaoImpl extends SessionFactoryHolder<City> implements CityDao {

    @Override
    public long addCity(City city) {
        super.save(city);
        return city.getId();
    }

    @Override
    public Optional<City> getCity(Long id) {
        return super.get(id);
    }

    @Override
    public Optional<City> getCityByName(String name) {
        return getSession()
                .createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public Stream<City> getAllCity() {
        return getSession()
                .createQuery("SELECT c FROM City c", City.class)
                .stream();
    }

    @Override
    public Stream<EventType> getEventTypesAtCity(Long id) {
        return getSession()
                .createQuery("SELECT et FROM City c " +
                        "JOIN c.events e " +
                        "JOIN e.eventTypes et " +
                        "WHERE c.id = :cityId " +
                        "GROUP BY et.id", EventType.class)
                .setParameter("cityId", id)
                .stream();
    }
}
