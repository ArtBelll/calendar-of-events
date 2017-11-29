package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Organisation;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrganisationDto {
    private Long id;
    private String name;
    private String legalName;
    private String workingHours;
    private String type;
    private Set<AddressDto> addresses;
    private Set<ContactDto> contacts;

    public OrganisationDto(Organisation organisation) {
        this.id = organisation.getId();
        this.name = organisation.getName();
        this.legalName = organisation.getLegalName();
        this.workingHours = organisation.getWorkingHours();
        this.type = organisation.getType();
        this.addresses = organisation.getAddresses()
                .stream()
                .map(AddressDto::new)
                .collect(Collectors.toSet());
        this.contacts = organisation.getContacts()
                .stream()
                .map(ContactDto::new)
                .collect(Collectors.toSet());
    }
}
