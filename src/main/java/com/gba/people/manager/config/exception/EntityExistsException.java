package com.gba.people.manager.config.exception;

import java.io.Serial;

public final class EntityExistsException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityExistsException(String message, String... args) {
        super(message, args);
    }

    public EntityExistsException(String message, Throwable cause, String... args) {
        super(message, cause, args);
    }
}
