package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.domain.User;

import java.util.Optional;
import java.util.UUID;

@Repository(value = "userDao")
public class UserDaoImpl extends SessionFactoryHolder<User> implements UserDao {

    @Override
    public Optional<User> get(UUID uuid) {
        return getSession()
                .createQuery("SELECT u FROM User u WHERE u.uuid = :uuid", User.class)
                .setParameter("uuid", uuid)
                .uniqueResultOptional();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return getSession()
                .createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .uniqueResultOptional();
    }
}
