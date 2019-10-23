package no.nav.pdl.fullmakt.app.common.rest.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
final class ApiError {

    @ApiModelProperty(example = "Validering feilet")
    private String message;

    @ApiModelProperty(example = "{\n" +
            "    \"objekt.path.feltnavn\" : [ \"valideringsfeil 1\", \"valideringsfeil 2\", \"valideringsfeil X\" ],\n" +
            "    \"fullmakt.fulmektig\" : [ \"Kan ikke være en fulmektig\", \"Fødselsnummer må være 11 siffer\" ],\n" +
            "}")

    private Map<String, List<String>> details;

}