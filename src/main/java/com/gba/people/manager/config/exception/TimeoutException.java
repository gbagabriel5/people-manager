package com.gba.people.manager.config.exception;

import java.io.Serial;

public class TimeoutException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TimeoutException(String message, String... args) {
        super(message, args);
    }

    public TimeoutException(String message, Throwable cause, String... args) {
        super(message, cause, args);
    }
}
