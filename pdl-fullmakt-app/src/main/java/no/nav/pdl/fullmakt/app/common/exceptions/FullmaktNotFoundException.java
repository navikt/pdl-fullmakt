package no.nav.pdl.fullmakt.app.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FullmaktNotFoundException extends RuntimeException {
	public FullmaktNotFoundException(String message) {
		super(message);
	}
}
