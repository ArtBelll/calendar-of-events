package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Contact;

@Data
public class ContactDto {
    private long id;
    private String type;
    private String value;
    private String description;

    public ContactDto(Contact contact) {
        this.id = contact.getId();
        this.type = contact.getType();
        this.value = contact.getValue();
        this.description = contact.getDescription();
    }
}
