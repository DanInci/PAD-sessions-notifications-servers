package com.pad.xmen.ale.notifications.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundFailure extends RuntimeException {

    public NotFoundFailure(String message) {
        super(message);
    }

}
