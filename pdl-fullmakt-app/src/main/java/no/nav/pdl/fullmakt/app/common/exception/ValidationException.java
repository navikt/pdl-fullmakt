package no.nav.pdl.fullmakt.app.common.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;
import no.nav.pdl.fullmakt.app.common.rest.api.DetailedError;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends UnrecoverableException {
    private final List<DetailedError> details;

    public ValidationException(String message, List<DetailedError> details) {
        super(message);
        this.details = details;
    }
}
