package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.Email;

import java.util.stream.Stream;

public interface EmailDao extends GenericDao<Email> {

    Stream<Email> pop(Integer number);
}
