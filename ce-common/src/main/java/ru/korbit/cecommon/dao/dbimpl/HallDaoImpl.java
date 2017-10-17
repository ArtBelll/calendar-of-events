package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.HallDao;
import ru.korbit.cecommon.domain.Hall;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@Repository(value = "hallDao")
public class HallDaoImpl extends SessionFactoryHolder implements HallDao {

    @Override
    public long addHall(Hall hall) {
        super.save(hall);
        return hall.getId();
    }

    @Override
    public Optional<Hall> getHall(Long id) {
        return getSession().byId(Hall.class).loadOptional(id);
    }

    @Override
    public Optional<Hall> getHallByRamblerId(Integer id) {
        return getSession()
                .createQuery("SELECT h FROM Hall h WHERE h.ramblerId = :id", Hall.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }
}
