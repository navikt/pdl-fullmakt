package no.nav.pdl.fullmakt.app.common.security.oidc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.pdl.fullmakt.app.common.exception.UnrecoverableException;
import org.jose4j.base64url.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OidcTokenUtil {

    @Autowired
    private ObjectMapper objectMapper;

    public String getSubject(String consumerToken) {
        try {
            byte[] consumerTokenBody = Base64.decode(consumerToken.split("\\.")[1]);
            return objectMapper.readTree(consumerTokenBody).get("sub").asText();
        } catch (Exception e) {
            throw new UnrecoverableException("Uthenting av subject fra consumer token feilet.", e);
        }
    }
}
