package no.nav.pdl.fullmakt.app.fullmakt;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.pdl.fullmakt.app.AppStarter;
import no.nav.pdl.fullmakt.app.common.exceptions.FullmaktNotFoundException;
//import no.nav.pdl.fullmakt.app.common.exceptions.ValidationException;
import no.nav.pdl.fullmakt.app.common.utils.JsonUtils;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FullmaktController.class)
@ContextConfiguration(classes = AppStarter.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class FullmaktControllerTest {


    private ObjectMapper objectMapper = JsonUtils.getObjectMapper();

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
        when(service.update(ArgumentMatchers.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

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

        String inputJson = objectMapper.writeValueAsString(request);

        mvc.perform(put("/fullmakt")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", is(11)));
    }


    /*
    @Test
    public void getFullmaktByListName_shouldReturnFullmaktForProducer() throws Exception {
        String uri = "/fullmakt/PRODUCER";

        MockHttpServletResponse response = mvc.perform(get(uri))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Map mappedResponse = objectMapper.readValue(response.getContentAsString(), HashMap.class);
        assertThat(mappedResponse, is(FullmaktService.fullmakter.get(ListName.PRODUCER)));
    }

    @Test
    public void getFullmaktByListName_shouldReturnNotFound_whenUnknownListName() throws Exception {
        String uri = "/fullmakt/UNKNOWN_LISTNAME";
        doThrow(new FullmaktNotFoundException("Fullmakt with listName=UNKNOWN_LISTNAME does not exist"))
                .when(service).validateListNameExists(anyString());

        Exception exception = mvc.perform(get(uri))
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException();

        assertThat(exception.getLocalizedMessage(), equalTo("Fullmakt with listName=UNKNOWN_LISTNAME does not exist"));
    }

    @Test
    public void getDescriptionByListNameAndCode_shouldReturnDescriptionForARBEIDSGIVER() throws Exception {
        String uri = "/fullmakt/PRODUCER/ARBEIDSGIVER";

        MockHttpServletResponse response = mvc.perform(get(uri))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getContentAsString(), is("Arbeidsgiver"));
    }

    @Test
    public void getDescriptionByListNameAndCode_shouldReturnNotFound_whenUnknownCode() throws Exception {
        String uri = "/fullmakt/PRODUCER/UNKNOWN_CODE";
        doThrow(new FullmaktNotFoundException("The code=UNKNOWN_CODE does not exist in the list=PRODUCER."))
                .when(service).validateListNameAndCodeExists(anyString(), anyString());

        Exception exception = mvc.perform(get(uri))
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException();

        assertThat(exception.getLocalizedMessage(), equalTo("The code=UNKNOWN_CODE does not exist in the list=PRODUCER."));
    }

    @Test
    public void save_shouldSave10Fullmakter() throws Exception {
        when(service.save(ArgumentMatchers.anyList())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        List<FullmaktRequest> requests = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> FullmaktRequest.builder()
                        .list(ListName.PRODUCER)
                        .code("CODE_nr:" + i)
                        .description("Description")
                        .build())
                .collect(Collectors.toList());

        String inputJson = objectMapper.writeValueAsString(requests);

        mvc.perform(post("/fullmakt")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()", is(10)));
    }

    @Test
    public void save_shouldReturnBadRequestWhenFailsValidation() throws Exception {
        String errorMessage = "The request was not accepted because it is empty";
        doThrow(new ValidationException(errorMessage)).when(service).validateRequests(anyList(), anyBoolean());

        String inputJson = objectMapper.writeValueAsString(Collections.emptyList());

        Exception exception = mvc.perform(post("/fullmakt").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException();

        assertTrue(exception.getLocalizedMessage().contains(errorMessage));
    }

    @Test
    public void update_shouldUpdateFullmakt() throws Exception {
        when(service.update(ArgumentMatchers.anyList())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        List<FullmaktRequest> requests = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> FullmaktRequest.builder()
                        .list(ListName.PRODUCER)
                        .code("CODE_nr:" + i)
                        .description("Description")
                        .build())
                .collect(Collectors.toList());

        String inputJson = objectMapper.writeValueAsString(requests);

        mvc.perform(put("/fullmakt")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", is(10)));
    }

    @Test
    public void update_shouldReturnBadRequest_withEmptyListOfRequests() throws Exception {
        String errorMessage = "The request was not accepted because it is empty";
        doThrow(new ValidationException(errorMessage)).when(service).validateRequests(anyList(), anyBoolean());

        String inputJson = objectMapper.writeValueAsString(Collections.emptyList());

        Exception exception = mvc.perform(
                MockMvcRequestBuilders.put("/fullmakt")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(inputJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException();

        assertTrue(exception.getLocalizedMessage().contains(errorMessage));
    }

    @Test
    public void delete_shouldDeleteFullmaktItem() throws Exception {
        String code = "TEST_DELETE";
        String uri = "/fullmakt/PRODUCER/TEST_DELETE";
        MockHttpServletResponse response = mvc.perform(
                delete(uri))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        verify(service).delete(ListName.PRODUCER, code);
    }

    @Test
    public void delete_shouldDelete_withoutCorrectFormat() throws Exception {
        String code = "TEST_DELETE";

        MockHttpServletResponse response = mvc.perform(
                delete("/fullmakt/producer/test_DELETE"))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        verify(service).delete(ListName.PRODUCER, code);
    }
    */
}