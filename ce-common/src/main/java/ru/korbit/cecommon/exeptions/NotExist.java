package ru.korbit.cecommon.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Artur Belogur on 24.10.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotExist extends RuntimeException {

    public NotExist(String message) {
        super(message);
    }

}
