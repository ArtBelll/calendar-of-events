package ru.korbit.ceadmin.dto;

import lombok.Data;
import ru.korbit.cecommon.domain.Contact;

@Data
public class RContact {
    private String type;
    private String value;
    private String description;

    public RContact(Contact contact) {
        this.type = contact.getType();
        this.value = contact.getValue();
        this.description = contact.getDescription();
    }
}
