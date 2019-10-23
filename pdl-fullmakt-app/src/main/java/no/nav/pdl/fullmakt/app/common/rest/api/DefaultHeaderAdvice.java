package no.nav.pdl.fullmakt.app.common.rest.api;

import io.swagger.annotations.ApiParam;
import lombok.val;
import no.nav.pdl.fullmakt.app.common.security.oidc.OidcTokenUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import static no.nav.pdl.fullmakt.app.common.Globals.NAV_CALL_ID;
import static no.nav.pdl.fullmakt.app.common.Globals.NAV_CONSUMER_TOKEN;

@ControllerAdvice(basePackageClasses = OpphoerAnnullerController.class)
public class DefaultHeaderAdvice {

    public static final String SYSTEM_KILDE = "system-kilde";

    @Autowired
    private OidcTokenUtil consumerTokenUtil;

    @ModelAttribute
    public void requiredHeaders(
            @ApiParam(value = NAV_CONSUMER_TOKEN, required = true) @RequestHeader(NAV_CONSUMER_TOKEN) String consumerToken,
            @ApiParam(value = NAV_CALL_ID) @RequestHeader(value = NAV_CALL_ID, required = false) String callId) {

        val systemKilde = consumerTokenUtil.getSubject(consumerToken);
        MDC.put(SYSTEM_KILDE, systemKilde);
        MDC.put(NAV_CALL_ID, callId);
    }
}
