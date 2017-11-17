package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.domain.User;

@Repository(value = "userDao")
public class UserDaoImpl extends SessionFactoryHolder<User> implements UserDao {
}
