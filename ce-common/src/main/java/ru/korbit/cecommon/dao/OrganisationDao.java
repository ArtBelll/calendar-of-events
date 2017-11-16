package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Organisation;

import java.util.Optional;

public interface OrganisationDao extends GenericDao<Organisation> {

    Optional<Organisation> getByName(String name);
}
