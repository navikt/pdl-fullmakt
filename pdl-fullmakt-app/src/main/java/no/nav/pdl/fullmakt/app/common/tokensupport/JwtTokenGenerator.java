package no.nav.pdl.fullmakt.app.common.tokensupport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenGenerator {

    public static final int TOKEN_MAX_AGE_MINUTES = 9;

}