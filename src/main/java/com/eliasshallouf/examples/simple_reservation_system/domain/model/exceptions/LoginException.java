package com.eliasshallouf.examples.simple_reservation_system.domain.model.exceptions;

public class LoginException extends RuntimeException {
    public LoginException(String cause) {
        super(cause);
    }
}
