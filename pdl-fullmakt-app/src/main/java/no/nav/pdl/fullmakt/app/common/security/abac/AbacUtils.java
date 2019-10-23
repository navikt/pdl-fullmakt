package no.nav.pdl.fullmakt.app.common.security.abac;

import lombok.experimental.UtilityClass;
import no.nav.tjenester.person.pdl.endring.v1.Type;

import java.util.Map;

@UtilityClass
public class AbacUtils {

    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_ADRESSE = "no.nav.abac.attributter.resource.navperson.adresse";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_DOEDSFALL = "no.nav.abac.attributter.resource.navperson.doedsfall";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_KOMMUNIKASJON = "no.nav.abac.attributter.resource.navperson.kommunikasjon";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_KONTONUMMER = "no.nav.abac.attributter.resource.navperson.kontonummer";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_NAVN = "no.nav.abac.attributter.resource.navperson.navn";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_SIKKERHETSTILTAK = "no.nav.abac.attributter.resource.navperson.sikkerhetstiltak";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_STATSBORGERSKAP = "no.nav.abac.attributter.resource.navperson.statsborgerskap";
    public static final String ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_UTENLANDSK_ID = "no.nav.abac.attributter.resource.navperson.utenlandsk_id";

    public static final Map<String, String> OPPYSNING_ATTRIBUTE_MAP = Map.of(
            Type.DOED.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_DOEDSFALL,
            Type.KONTONUMMER.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_KONTONUMMER,
            Type.NAVN.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_NAVN,
            Type.NORSK_KONTAKTADRESSE.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_ADRESSE,
            Type.SIKKERHETSTILTAK.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_SIKKERHETSTILTAK,
            Type.STATSBORGERSKAP.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_STATSBORGERSKAP,
            Type.TELEFONNUMMER.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_KOMMUNIKASJON,
            Type.UTENLANDSK_KONTAKTADRESSE.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_ADRESSE,
            Type.UTENLANDSKIDENTIFIKASJONSNUMMER.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_UTENLANDSK_ID,
            Type.SPRAAK_MAALFORM.getType().getSimpleName(), ABAC_ATTRIBUTTER_RESOURCE_NAVPERSON_KOMMUNIKASJON
    );
}
