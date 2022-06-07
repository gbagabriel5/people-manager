package com.gba.people.manager.config.exception;

import java.io.Serial;

public class InvalidCpfException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCpfException(String message, String... args) {
        super(message, args);
    }
}