package no.nav.pdl.fullmakt.app.common.rest.api;

import lombok.val;
import no.nav.pdl.fullmakt.app.common.exception.ConflictException;
import no.nav.pdl.fullmakt.app.common.exception.LockedException;
import no.nav.pdl.fullmakt.app.common.exception.NotFoundException;
import no.nav.pdl.fullmakt.app.common.exception.PersondokumentException;
import no.nav.pdl.fullmakt.app.common.exception.UnauthorizedException;
import no.nav.pdl.fullmakt.app.common.exception.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.LOCKED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandlerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(PersondokumentException.class)
    public ApiError handlePersondokumentException(PersondokumentException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiError handleNotFoundException(UnauthorizedException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiError handleNotFoundException(NotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ApiError handleConflictException(ConflictException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public ApiError handleValidationException(ValidationException e) {
        Map<String, List<String>> details = new HashMap<>();
        val detailedErrors = e.getDetails();

        detailedErrors.forEach(detailedError -> {
            val fieldPath = detailedError.getName();
            val message = detailedError.getMessage();
            List<String> list = details.getOrDefault(fieldPath, new ArrayList<>());
            list.add(message);
            details.put(fieldPath, list);
        });
        return ApiError.builder()
                .message("Validering feilet")
                .details(details)
                .build();
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationException(MethodArgumentNotValidException e) {
        Map<String, List<String>> details = new HashMap<>();
        val globalErrors = e.getBindingResult().getGlobalErrors();
        val fieldErrors = e.getBindingResult().getFieldErrors();

        fieldErrors.forEach(error -> {
            val fieldPath = getPathFromFieldError(error);
            val message = error.getDefaultMessage() != null ? error.getDefaultMessage() : "";
            List<String> list = details.getOrDefault(fieldPath, new ArrayList<>());
            list.add(message);
            details.put(fieldPath, list);
        });

        globalErrors.forEach(error -> {
            val objectName = error.getObjectName();
            val message = error.getDefaultMessage() != null ? error.getDefaultMessage() : "";
            List<String> list = details.getOrDefault(objectName, new ArrayList<>());
            list.add(message);
            details.put(objectName, list);
        });
        return ApiError.builder()
                .message("Validering feilet")
                .details(details)
                .build();
    }

    @ResponseStatus(LOCKED)
    @ExceptionHandler(LockedException.class)
    public ApiError handleLockedException(LockedException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .details(null)
                .build();
    }

    private String getPathFromFieldError(FieldError fieldError) {
        return fieldError.getObjectName() + "." + fieldError.getField();
    }
}
