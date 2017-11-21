package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Organisation;

@Data
public class ROrganisation {
    private Long id;
    private String email;
    private String name;

    public ROrganisation(Organisation organisation) {
        this.id = organisation.getId();
        this.name = organisation.getName();
        this.email = organisation.getEmail();
    }
}
