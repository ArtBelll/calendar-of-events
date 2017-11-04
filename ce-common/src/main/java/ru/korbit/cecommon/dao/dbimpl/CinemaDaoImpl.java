package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.CinemaDao;
import ru.korbit.cecommon.domain.Cinema;
import ru.korbit.cecommon.domain.City;

import java.util.Optional;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@Repository(value = "cinemaDao")
public class CinemaDaoImpl extends SessionFactoryHolder<Cinema> implements CinemaDao {

    CinemaDaoImpl() {
        super(Cinema.class);
    }

    @Override
    public Optional<Cinema> getByName(String name) {
        return getSession()
                .createQuery("SELECT c FROM Cinema c WHERE c.name = :name", Cinema.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }
}
