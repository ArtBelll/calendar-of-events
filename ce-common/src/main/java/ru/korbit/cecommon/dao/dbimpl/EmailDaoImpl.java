package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.domain.Email;

@Repository(value = "emailDao")
public class EmailDaoImpl extends SessionFactoryHolder<Email> implements EmailDao {
}
