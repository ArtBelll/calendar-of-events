package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.domain.Email;

import java.util.stream.Stream;

@Repository(value = "emailDao")
public class EmailDaoImpl extends SessionFactoryHolder<Email> implements EmailDao {

    @Override
    public Stream<Email> pop(Integer number) {
        return getSession()
                .createQuery("SELECT e FROM Email e " +
                        "ORDER BY e.firstAttempt ", Email.class)
                .stream()
                .limit(number);
    }
}
