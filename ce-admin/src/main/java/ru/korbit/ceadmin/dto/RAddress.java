package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Address;

@Data
public class RAddress {
    private String city;
    private String street;
    private String house;
    private int postcode;

    public RAddress(Address address) {
        this.city = address.getCity().getName();
        this.street = address.getStreet();
        this.house = address.getHouse();
        this.postcode = address.getPostcode();
    }
}
