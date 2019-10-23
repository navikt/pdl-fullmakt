package no.nav.pdl.fullmakt.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends UnrecoverableException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
