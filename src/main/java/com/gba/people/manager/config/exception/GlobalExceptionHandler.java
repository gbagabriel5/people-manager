package com.gba.people.manager.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String UNEXPECTED_ERROR = "Erro inesperado. {0}.";
    private static final String URL_NOT_FOUND_ERROR = "Caminho inv\\u00E1lido. {0}.";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNotFoundException(NotFoundException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), NOT_FOUND, ex);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<RestErrorMessage> handleEntityExistsException(EntityExistsException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), BAD_REQUEST, ex);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestErrorMessage> handleNullPointerException(NullPointerException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), BAD_REQUEST, ex);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<RestErrorMessage> handleTimeoutException(TimeoutException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), BAD_REQUEST, ex);
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<RestErrorMessage> handleIllegalArgument(GlobalException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), BAD_REQUEST, ex);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidCpfException(InvalidCpfException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), BAD_REQUEST, ex);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidEmailException(InvalidEmailException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(locale, ex.getMessage(), ex.getArgs(), BAD_REQUEST, ex);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNoHandlerFoundException(NoHandlerFoundException ex, Locale locale) {
        logInfo(ex);
        return getRestErrorMessageResponseEntity(
                locale, URL_NOT_FOUND_ERROR, new String[]{ex.getMessage()}, BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            Locale locale
    ) {
        logInfo(ex);
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> {
                    Object[] args = objectError.getArguments();
                    if (args != null && args.length > 1) {
                        // Remove a mensagem padrão da lista
                        args = Arrays.copyOfRange(args, 1, args.length);
                    }
                    return messageSource.getMessage(requireNonNull(objectError.getDefaultMessage()), args, locale);
                })
                .collect(Collectors.toList());

        RestErrorMessage restErrorMessage;
        if (errorMessages.isEmpty()) {
            restErrorMessage = new RestErrorMessage(ex);
        } else if (errorMessages.size() == 1) {
            restErrorMessage = new RestErrorMessage(errorMessages.get(0), ex);
        } else {
            restErrorMessage = new RestErrorMessage(errorMessages, ex);
        }
        return new ResponseEntity<>(restErrorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleExceptions(Exception ex, Locale locale) {
        logError(ex);
        return getRestErrorMessageResponseEntity(
                locale, UNEXPECTED_ERROR, new String[]{ex.getMessage()}, INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<RestErrorMessage> getRestErrorMessageResponseEntity(
            Locale locale,
            String message,
            String[] args,
            HttpStatus httpStatus,
            Exception ex
    ) {
        String errorMessage = "";
        if (message != null) {
            errorMessage = messageSource.getMessage(message, args, message, locale);
        }
        return new ResponseEntity<>(new RestErrorMessage(errorMessage, ex), httpStatus);
    }

    private void logError(Exception ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void logInfo(Exception ex) {
        if (log.isInfoEnabled()) {
            log.info(ex.getMessage(), ex);
        }
    }
}