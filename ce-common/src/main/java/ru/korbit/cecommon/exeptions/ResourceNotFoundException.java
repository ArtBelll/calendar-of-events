package ru.korbit.cecommon.exeptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Created by Sergey Ignatov on 24/02/16.
 */
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String name;
    private long id = 0;
    private UUID uuid = null;

    public ResourceNotFoundException(final String login, long id) {
        this.name = login;
        this.id = id;
    }

    public ResourceNotFoundException(final String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
