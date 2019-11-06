package no.nav.pdl.fullmakt.app.omraade;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class OmraadeService {

    public Object getAllOmraade() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object testObj = mapper.readValue(OmraadeData.omraadeData, Object.class);
            return testObj;
        } catch (IOException ie) {
            log.error("Error i getAllOmråde");
            return null;
        }
    }
}
