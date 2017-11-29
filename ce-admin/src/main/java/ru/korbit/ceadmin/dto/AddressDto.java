package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Address;

@Data
public class AddressDto {
    private Long id;
    private String city;
    private String street;
    private String house;
    private Integer postcode;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.house = address.getHouse();
        this.postcode = address.getPostcode();
    }
}
