package ru.korbit.cecommon.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Sergey Ignatov on 14/03/16.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorized extends RuntimeException {
    public UnAuthorized(final String message) {
        super(message);
    }
}
