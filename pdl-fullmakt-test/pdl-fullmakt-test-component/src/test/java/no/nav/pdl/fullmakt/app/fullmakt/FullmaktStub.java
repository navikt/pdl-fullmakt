package no.nav.pdl.fullmakt.app.fullmakt;

import no.nav.pdl.fullmakt.app.fullmakt.FullmaktService;
import no.nav.pdl.fullmakt.app.fullmakt.ListName;

import java.util.Map;

public class FullmaktStub {

    public static void initializeFullmakt() {
        Map<ListName, Map<String, String>> fullmakter = FullmaktService.fullmakter;
        fullmakter.get(ListName.PRODUCER).put("ARBEIDSGIVER", "Arbeidsgiver");
        fullmakter.get(ListName.PRODUCER).put("SKATTEETATEN", "Skatteetaten");
        fullmakter.get(ListName.PRODUCER).put("BRUKER", "Bruker");
        fullmakter.get(ListName.CATEGORY).put("PERSONALIA", "Personalia");
        fullmakter.get(ListName.CATEGORY).put("ARBEIDSFORHOLD", "Arbeidsforhold");
        fullmakter.get(ListName.CATEGORY).put("UTDANNING", "Utdanning");
        fullmakter.get(ListName.SYSTEM).put("TPS", "Tjenestebasert PersondataSystem");
        fullmakter.get(ListName.SYSTEM).put("PESYS", "Pensjon");
    }
}
