package no.nav.pdl.fullmakt.app.omraade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import no.nav.pdl.fullmakt.app.fullmakt.FullmaktController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/fullmakt/omraade")
@Api(value = "Område", description = "REST API for område", tags = { "Omraade" })
public class OmraadeController {


    private static final Logger logger = LoggerFactory.getLogger(FullmaktController.class);

    @Autowired
    private OmraadeService service;

    @ApiOperation(value = "Hent alle område", tags = {"Omraade"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Områder hentet", response = String.class),
            @ApiResponse(code = 404, message = "Fant ikke områder"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping()
    public List<Omraade> getAllOmraade() {
        logger.info("Fikk en forespørsel om alle områder");
        return service.getAllOmraade();
    }

}
