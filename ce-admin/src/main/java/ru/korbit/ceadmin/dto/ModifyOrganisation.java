package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Address;
import ru.korbit.cecommon.domain.Contact;

import java.util.Set;

@Data
public class ModifyOrganisation {
    private String name;
    private String legalName;
    private String workingHours;
    private String type;
    private Set<Address> addresses;
    private Set<Contact> contacts;
}
