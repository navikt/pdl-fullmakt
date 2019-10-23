package no.nav.pdl.fullmakt.app.common.rest.api;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.BRUKER_MANGLER_TILGANG;
import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.DUBLETT_FUNNET;
import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.FINNER_IKKE_PERSON_ELLER_OPPLYSNING;
import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.NAV_CONSUMER_ID_MANLGER_NAV_PERSONIDENT_MANGLER;
import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.OK;
import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.OPPLYSNING_LOCKED;
import static no.nav.pdl.fullmakt.app.common.rest.api.GeneralHttpResponse.UGYLDIG_OIDC_TOKEN;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(code = 201, message = OK),
        @ApiResponse(code = 400, message = NAV_CONSUMER_ID_MANLGER_NAV_PERSONIDENT_MANGLER),
        @ApiResponse(code = 401, message = UGYLDIG_OIDC_TOKEN),
        @ApiResponse(code = 403, message = BRUKER_MANGLER_TILGANG),
        @ApiResponse(code = 404, message = FINNER_IKKE_PERSON_ELLER_OPPLYSNING),
        @ApiResponse(code = 409, message = DUBLETT_FUNNET),
        @ApiResponse(code = 423, message = OPPLYSNING_LOCKED)
})
public @interface GeneralHttpResponse {
    String OK = "OK";
    String NAV_CONSUMER_ID_MANLGER_NAV_PERSONIDENT_MANGLER = "Nav-Consumer-Id manlger || Nav-Personident mangler";
    String UGYLDIG_OIDC_TOKEN = "Ugyldig oidc token";
    String BRUKER_MANGLER_TILGANG = "Bruker mangler tilgang";
    String FINNER_IKKE_PERSON_ELLER_OPPLYSNING = "Finner ikke person eller opplysning";
    String DUBLETT_FUNNET = "Dublett funnet";
    String OPPLYSNING_LOCKED = "Pågående endring for opplysningstype på person";
}