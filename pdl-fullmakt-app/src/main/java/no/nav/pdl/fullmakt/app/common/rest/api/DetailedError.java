package no.nav.pdl.fullmakt.app.common.rest.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class DetailedError {
    private String name;
    private String message;
}
