package no.nav.pdl.fullmakt.app.common.exception;

public class LockedException extends UnrecoverableException {
    public LockedException(String msg) {
        super(msg);
    }
}
