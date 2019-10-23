package no.nav.pdl.fullmakt.app.common.exception;

public class NotFoundException extends RecoverableException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
