package com.gba.people.manager.config.exception;

import java.io.Serial;

public class InvalidEmailException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidEmailException(String message, String... args) {
        super(message, args);
    }
}