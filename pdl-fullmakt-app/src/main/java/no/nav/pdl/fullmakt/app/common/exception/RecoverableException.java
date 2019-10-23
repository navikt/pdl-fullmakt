package no.nav.pdl.fullmakt.app.common.exception;

public class RecoverableException extends RuntimeException {

    public RecoverableException(String message) {
        super(message);
    }

    public RecoverableException(String message, Throwable cause) {
        super(message, cause);
    }
}
