package ru.korbit.cecommon.dao.dbimpl;

import ru.korbit.cecommon.dao.OrganisationDao;
import ru.korbit.cecommon.domain.Organisation;

import java.util.Optional;

public class OrganisationDaoImpl extends SessionFactoryHolder<Organisation> implements OrganisationDao {

    @Override
    public Optional<Organisation> getByName(String name) {
        return getSession()
                .createQuery("SELECT o FROM Organisation o WHERE o.name = :name", Organisation.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }
}
