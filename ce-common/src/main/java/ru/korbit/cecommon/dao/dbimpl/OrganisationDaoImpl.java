package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.OrganisationDao;
import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.packet.StatusOfOrganisation;

import java.util.Optional;
import java.util.stream.Stream;

@Repository(value = "organisationDao")
public class OrganisationDaoImpl extends SessionFactoryHolder<Organisation> implements OrganisationDao {

    @Override
    public Optional<Organisation> getByName(String name) {
        return getSession()
                .createQuery("SELECT o FROM Organisation o WHERE o.name = :name", Organisation.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public Stream<Organisation> getByStatus(StatusOfOrganisation status) {
        return getSession()
                .createQuery("SELECT o FROM Organisation o WHERE o.status = :status", Organisation.class)
                .setParameter("status", status)
                .stream();
    }

    @Override
    public Optional<Organisation> searchByIdAndStatus(Long id, StatusOfOrganisation status) {
        return getSession()
                .createQuery("SELECT o FROM Organisation o " +
                        "WHERE o.id = :id AND o.status = :status", Organisation.class)
                .setParameter("id", id)
                .setParameter("status", status)
                .uniqueResultOptional();
    }
}
