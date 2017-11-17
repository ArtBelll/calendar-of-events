package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.packet.StatusOfOrganisation;

import java.util.Optional;
import java.util.stream.Stream;

public interface OrganisationDao extends GenericDao<Organisation> {

    Optional<Organisation> getByName(String name);

    Stream<Organisation> getByStatus(StatusOfOrganisation status);

    Optional<Organisation> searchByIdAndStatus(Long id, StatusOfOrganisation status);
}
