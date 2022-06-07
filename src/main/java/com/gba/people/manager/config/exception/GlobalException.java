package com.gba.people.manager.config.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final String[] args;

    public GlobalException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public GlobalException(String message, Throwable cause, String... args) {
        super(message, cause);
        this.args = args;
    }
}