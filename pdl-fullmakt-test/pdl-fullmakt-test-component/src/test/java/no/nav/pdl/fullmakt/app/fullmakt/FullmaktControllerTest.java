package no.nav.pdl.fullmakt.app.fullmakt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.pdl.fullmakt.app.AppStarter;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import no.nav.pdl.fullmakt.app.common.exceptions.ValidationException;
// import no.nav.pdl.fullmakt.app.common.utils.JsonUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(FullmaktController.class)
@ContextConfiguration(classes = AppStarter.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class FullmaktControllerTest {


    //  private ObjectMapper objectMapper = JsonUtils.getObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FullmaktService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        // FullmaktStub.initializeFullmakt();
    }

    @Test
    public void findAll_shouldReturnTheInitiatedFullmakt() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/fullmakt"))
                .andReturn().getResponse();

        // Fullmakt returnedFullmakt = objectMapper.readValue(response.getContentAsString(), Fullmakt.class);

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        //assertThat(returnedFullmakt.size(), is(service.getAllFullmakt().size()));

    }


    @Test
    public void update_shouldUpdateFullmakt() throws Exception {
        when(service.save(ArgumentMatchers.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Fullmakt request =  Fullmakt.builder()
                .ednretAv("")
                .endret(DateUtil.now())
                .fullmaktId(1L)
                .opphoert(false)

                .fullmaktsgiver("123")
                .fullmektig("321")
                .omraade("Område test")
                .gyldigFraOgMed(DateUtil.now())
                .gyldigTilOgMed(DateUtil.now())
                .registrert(DateUtil.now())
                .registrertAv("123")
                .build();
        String inputJson = getObjectMapper().writeValueAsString(request);

        mvc.perform(put("/fullmakt")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", is(11)));
    }

    private ObjectMapper getObjectMapper() throws JsonProcessingException {
        return new ObjectMapper();
    }

}