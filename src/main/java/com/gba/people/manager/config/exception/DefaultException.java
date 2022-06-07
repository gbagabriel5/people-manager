package com.gba.people.manager.config.exception;

import java.io.Serial;

public class DefaultException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DefaultException(String message) {
        super(message);
    }
}