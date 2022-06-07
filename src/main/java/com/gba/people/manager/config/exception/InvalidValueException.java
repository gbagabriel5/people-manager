package com.gba.people.manager.config.exception;

import lombok.Getter;
import java.io.Serial;

@Getter
public class InvalidValueException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String[] args;


    public InvalidValueException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public InvalidValueException(String message, Throwable cause, String... args) {
        super(message, cause);
        this.args = args;
    }
}
