package com.gba.people.manager.config.exception;

import lombok.Getter;
import java.util.List;

@Getter
public class RestErrorMessage {

    private final String message;
    private final List<String> messages;
    private final String exceptionMessage;

    public RestErrorMessage(List<String> messages, Exception ex) {
        this(null, messages, ex);
    }

    public RestErrorMessage(String message, Exception ex) {
        this(message, null, ex);
    }

    public RestErrorMessage(Exception ex) {
        this(null, null, ex);
    }

    private RestErrorMessage(String message, List<String> messages, Exception ex) {
        this.message = message;
        this.messages = messages;
        this.exceptionMessage = exceptionToString(ex);
    }

    private String exceptionToString(Exception exceptionMessage) {
        if (exceptionMessage != null) {
            return exceptionMessage.toString();
        }
        return null;
    }
}
