package ru.korbit.cecommon.dao;

import ru.korbit.cecommon.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends GenericDao<User> {

    Optional<User> get(UUID uuid);

    Optional<User> getByEmail(String email);
}
