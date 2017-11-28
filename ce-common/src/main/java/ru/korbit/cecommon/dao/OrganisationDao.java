package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.packet.StatusOfOrganisation;

import java.util.Optional;
import java.util.stream.Stream;

public interface OrganisationDao extends GenericDao<Organisation> {

    Optional<Organisation> getByEmail(String email);

    Stream<Organisation> getByStatus(StatusOfOrganisation status);

    Optional<Organisation> searchByIdAndStatus(Long id, StatusOfOrganisation status);
}
