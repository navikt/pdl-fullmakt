package no.nav.pdl.fullmakt.app.common.security.abac;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class AbacUtils {

    public static final String ABAC_ATTRIBUTTER_RESOURCE_EKSTERNBRUKER_FULLMAKT = "no.nav.abac.attributter.resource.eksternbruker.fullmakt";

    public static final Map<String, String> OPPYSNING_ATTRIBUTE_MAP = Map.of(
            "Fullmakt", ABAC_ATTRIBUTTER_RESOURCE_EKSTERNBRUKER_FULLMAKT
    );
}
