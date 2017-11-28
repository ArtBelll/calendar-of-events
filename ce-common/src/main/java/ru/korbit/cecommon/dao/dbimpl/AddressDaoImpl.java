package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.AddressDao;
import ru.korbit.cecommon.domain.Address;

@Repository(value = "addressDao")
public class AddressDaoImpl extends SessionFactoryHolder<Address> implements AddressDao {

    public AddressDaoImpl() {
        super(Address.class);
    }
}
