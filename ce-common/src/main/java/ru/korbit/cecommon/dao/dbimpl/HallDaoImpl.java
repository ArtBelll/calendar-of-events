package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.HallDao;
import ru.korbit.cecommon.domain.Hall;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
@Repository(value = "hallDao")
public class HallDaoImpl extends SessionFactoryHolder<Hall> implements HallDao {

    HallDaoImpl() {
        super(Hall.class);
    }

    @Override
    public Optional<Hall> getByRamblerId(String id) {
        return getSession()
                .createQuery("SELECT h FROM Hall h WHERE h.ramblerId = :id", Hall.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }
}
