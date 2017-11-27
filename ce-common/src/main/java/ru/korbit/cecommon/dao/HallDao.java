package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Hall;

import java.util.Optional;

/**
 * Created by Artur Belogur on 17.10.17.
 */
public interface HallDao extends GenericDao<Hall> {

    Optional<Hall> getByThirdPartyId(String id);
}
