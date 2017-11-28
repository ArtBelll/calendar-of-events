package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.ContactDao;
import ru.korbit.cecommon.domain.Contact;

@Repository(value = "contactDao")
public class ContactDaoImpl extends SessionFactoryHolder<Contact> implements ContactDao {

    public ContactDaoImpl() {
        super(Contact.class);
    }
}
