package com.dawist_o.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "There is no such user")
public class InvalidRegistrationException extends RuntimeException {
    public InvalidRegistrationException() {
    }

    public InvalidRegistrationException(String message) {
        super(message);
    }
}
