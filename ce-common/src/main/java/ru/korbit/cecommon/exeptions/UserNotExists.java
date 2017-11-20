package ru.korbit.cecommon.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Sergey Ignatov on 04/08/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotExists extends RuntimeException {
    public UserNotExists(final String login) { super("Account with this login doesn't exist:" + login); }
}
