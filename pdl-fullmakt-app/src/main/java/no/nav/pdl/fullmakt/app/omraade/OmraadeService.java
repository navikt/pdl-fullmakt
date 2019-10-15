package no.nav.pdl.fullmakt.app.omraade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OmraadeService {

    public List<Omraade> getAllOmraade()  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Omraade> testObj =  Arrays.asList( mapper.readValue(OmraadeData.omraadeData, Omraade[].class));
            return testObj;
        } catch (IOException ie)
        {
            log.error("Error i getAllOmråde");
            return null;
        }
    }
}
