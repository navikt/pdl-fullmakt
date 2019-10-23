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
            "    \"norskGateadresse.gatenavn\" : [ \"Kan ikke være en postboksadresse\", \"Makslengde 30 tegn\" ],\n" +
            "    \"norskGateadresse.postnummer\" : [ \"Postnummer er påkrevd\" ],\n" +
            "    \"norskGateadresse.kommunenummer\" : [ \"size must be between 4 and 4\" ],\n" +
            "    \"norskGateadresse.gyldigTom\" : [ \"Kan maks være gydlig i et år fra dagens dato\" ],\n" +
            "\t\"kontonummer.utenlandskKontoInformasjon.swift\": [\"Swift sine fire første tegn må være alfanumeriske.\", \"Swift sine femte og sjette tegn kan ikke inneholde tall.\", \"Swift sitt syvende og åttende tegn må være alfanumeriske.\"]\n" +
            "}")

    private Map<String, List<String>> details;

}