package no.nav.pdl.fullmakt.app.common.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirect {

    @GetMapping("/")
    public String redirect() {
        return "redirect:swagger-ui.html";
    }
}
