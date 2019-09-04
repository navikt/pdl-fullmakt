package no.nav.pdl.fullmakt.app.fullmakt;

import no.nav.pdl.fullmakt.app.common.exceptions.FullmaktNotFoundException;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// import static no.nav.pdl.fullmakt.app.fullmakt.FullmaktService.fullmakter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FullmaktServiceTest {

	@Mock
	private FullmaktRepository repository;

	@InjectMocks
	private FullmaktService service;

	@Test
	public void save_shouldCreateRequest() {
		Fullmakt request = Fullmakt.builder()
				.ednretAv("")
				.endret(DateUtil.now())
				//.fullmaktId(1L)
				.opphoert(false)
				.fullmaktsgiver("123")
				.fullmektig("321")
				.gyldigFraOgMed(DateUtil.now())
				.gyldigTilOgMed(DateUtil.now())
				.registrert(DateUtil.now())
				.registrertAv("123")
				.build();
		service.save(request);
		//verify(repository, times(1)).saveAll(anyList());
		//assertThat( repository.findByFullmaktId(1L).get().getFullmaktId(), is(1L));
	}

	/*
	@Test
	public void update_shouldUpdateRequest_whenRequestIsValid() {
		fullmakter.get(ListName.PRODUCER).put("TEST_UPDATE", "Original description");

		FullmaktRequest request = FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("TEST_UPDATE")
				.description("Updated description")
				.build();

		when(repository.findByListAndCode(request.getList(), request.getCode())).thenReturn(Optional.of(request.convert()));

		service.update(List.of(request));

		verify(repository, times(1)).saveAll(anyList());
		verify(repository, times(1)).findByListAndCode(any(ListName.class), anyString());
		assertThat(fullmakter.get(request.getList()).get(request.getCode()), is("Updated description"));
	}

	@Test
	public void update_shouldThrowNotFound_whenCodeDoesNotExist() {
		FullmaktRequest request = FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("UNKNOWN_CODE")
				.description("Updated description")
				.build();

		when(repository.findByListAndCode(ListName.PRODUCER, "UNKNOWN_CODE")).thenReturn(Optional.empty());

		try {
			service.update(List.of(request));
		} catch (FullmaktNotFoundException e) {
			assertThat(e.getLocalizedMessage(), is("Cannot find Fullmakt with code=UNKNOWN_CODE in list=PRODUCER"));
		}
	}

	@Test
	public void delete_shouldDelete_whenListAndCodeExists() {
		ListName listName = ListName.CATEGORY;
		String code = "TEST_DELETE";
		String description = "Test delete description";

		fullmakter.get(listName).put(code, description);
		Fullmakt fullmakt = Fullmakt.builder()
				.list(listName)
				.code(code)
				.description(description)
				.build();
		when(repository.findByListAndCode(listName, code)).thenReturn(Optional.of(fullmakt));

		service.delete(listName, code);

		verify(repository, times(1)).findByListAndCode(any(ListName.class), anyString());
		verify(repository, times(1)).delete(any(Fullmakt.class));
		assertNull(fullmakter.get(listName).get(code));
	}

	@Test
	public void delete_shouldThrowIllegalArgumentException_whenCodeDoesNotExist() {
		when(repository.findByListAndCode(ListName.PRODUCER, "UNKNOWN_CODE")).thenReturn(Optional.empty());


		try {
			service.delete(ListName.PRODUCER, "UNKNOWN_CODE");
		} catch (IllegalArgumentException e) {
			assertThat(e.getLocalizedMessage(), is("Cannot find a Fullmakt to delete with code=UNKNOWN_CODE and listName=PRODUCER"));
		}
	}

	@Test
	public void validateListNameExistsANDvalidateListNameAndCodeExists_nothingShouldHappen_whenValuesExists() {
		fullmakter.get(ListName.PURPOSE).put("CODE", "Description");

		service.validateListNameExists("PURPOSE");
		service.validateListNameAndCodeExists("PURPOSE", "CODE");
	}

	@Test
	public void validateListNameExists_shouldThrowNotFound_whenListNameDoesNotExists() {
		try {
			service.validateListNameExists("UNKNOWN_LISTNAME");
		} catch (FullmaktNotFoundException e) {
			assertThat(e.getLocalizedMessage(), is("Fullmakt with listName=UNKNOWN_LISTNAME does not exist"));
		}
	}

	@Test
	public void validateListNameAndCodeExists_shouldThrowNotFound_whenCodeDoesNotExists() {
		try {
			service.validateListNameAndCodeExists("PRODUCER", "unknownCode");
		} catch (FullmaktNotFoundException e) {
			assertThat(e.getLocalizedMessage(), is("The code=unknownCode does not exist in the list=PRODUCER."));
		}
	}

	@Test
	public void isListNamePresentInFullmakt_shouldReturnOptionalEmpty_whenListNameDoesNotExist() {
		boolean unknownListName = service.isListNamePresentInFullmakt("UnknownListName");

		assertFalse(unknownListName);
	}

	@Test
	public void validateRequestsCreate_shouldValidateListOfFullmaktRequests() {
		List<FullmaktRequest> requests = new ArrayList<>();
		requests.add(FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());
		requests.add(FullmaktRequest.builder()
				.list(ListName.SYSTEM)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());
		requests.add(FullmaktRequest.builder()
				.list(ListName.CATEGORY)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());

		service.validateRequests(requests, false);
	}

	@Test
	public void validateRequestsCreate_shouldThrowValidationException_withEmptyListOfRequests() {
		List<FullmaktRequest> requests = Collections.emptyList();
		try {
			service.validateRequests(requests, false);
		} catch (ValidationException e) {
			assertThat(e.getLocalizedMessage(), is("The request was not accepted because it is empty"));
		}
	}

	@Test
	public void validateRequestsCreate_shouldThrowValidationException_whenRequestHasEmptyValues() {
		FullmaktRequest request = FullmaktRequest.builder().build();
		try {
			service.validateRequests(List.of(request), false);
		} catch (ValidationException e) {
			assertThat(e.get().size(), is(1));
			assertThat(e.get().get("Request:1").size(), is(3));
			assertThat(e.get().get("Request:1").get("list"), is("The Fullmakt must have a listName"));
			assertThat(e.get().get("Request:1").get("code"), is("The code was null or missing"));
			assertThat(e.get().get("Request:1").get("description"), is("The description was null or missing"));
		}
	}

	@Test
	public void validateRequestsCreate_shouldThrowValidationException_whenFullmaktExistsInRepository() {
		FullmaktRequest request = FullmaktRequest.builder().list(ListName.PRODUCER).code("BRUKER").description("Test").build();
		service.save(List.of(request));
		try {
			service.validateRequests(List.of(request), false);
		} catch (ValidationException e) {
			assertThat(e.get().size(), is(1));
			assertThat(e.get().get("Request:1").size(), is(1));
			assertThat(e.get().get("Request:1").get("code"),
					is("The code BRUKER already exists in the Fullmakt(PRODUCER) and therefore cannot be created"));
		}
	}

	@Test
	public void validateRequestsCreate_shouldThrowValidationException_whenFullmaktExistsInRequest() {
		List<FullmaktRequest> requests = new ArrayList<>();
		requests.add(FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());
		requests.add(FullmaktRequest.builder()
				.list(ListName.SYSTEM)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());
		requests.add(FullmaktRequest.builder()
				.list(ListName.CATEGORY)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());
		requests.add(FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());

		try {
			service.validateRequests(requests, false);
		} catch (ValidationException e) {
			assertThat(e.get().size(), is(1));
			assertThat(e.get().get("NotUniqueRequests").size(), is(1));
			assertThat(e.get().get("NotUniqueRequests").get("PRODUCER-TEST"),
					is("Request:4 - The Fullmakt PRODUCER-TEST is not unique because it has already been used in this request (see request:1)"));
		}
	}

	@Test
	public void validateRequestUpdate_shouldValidate_whenFullmaktsExists() {
		List<FullmaktRequest> requests = new ArrayList<>();
		requests.add(FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("TEST")
				.description("Informasjon oppgitt av tester")
				.build());
		service.save(requests);

		service.validateRequests(requests, true);
	}

	@Test
	public void validateRequestsUpdate_shouldThrowValidationException_whenRequestHasEmptyValues() {
		FullmaktRequest request = FullmaktRequest.builder().build();
		try {
			service.validateRequests(List.of(request), true);
		} catch (ValidationException e) {
			assertThat(e.get().size(), is(1));
			assertThat(e.get().get("Request:1").size(), is(3));
			assertThat(e.get().get("Request:1").get("list"), is("The Fullmakt must have a listName"));
			assertThat(e.get().get("Request:1").get("code"), is("The code was null or missing"));
			assertThat(e.get().get("Request:1").get("description"), is("The description was null or missing"));
		}
	}

	@Test
	public void shouldThrowValidationExceptionOnUpdate_whenFullmaktDoesNotExist() {
		FullmaktRequest request = FullmaktRequest.builder()
				.list(ListName.PRODUCER)
				.code("unknownCode")
				.description("Test")
				.build();
		try {
			service.validateRequests(List.of(request), true);
		} catch (ValidationException e) {
			assertThat(e.get().size(), is(1));
			assertThat(e.get().get("Request:1").size(), is(1));
			assertThat(e.get().get("Request:1").get("code"),
					is("The code UNKNOWNCODE does not exist in the Fullmakt(PRODUCER) and therefore cannot be updated"));
		}
	}

	@Test
	public void validateRequest_shouldChangeCodeAndDescriptionInRequestToCorrectFormat() {
		List<FullmaktRequest> requests = List.of(FullmaktRequest.builder()
				.list(ListName.CATEGORY)
				.code("    cOrRecTFormAT  ")
				.description("   Trim av description                      ")
				.build());
		service.validateRequests(requests, false);
		service.save(requests);
		assertTrue(fullmakter.get(ListName.CATEGORY).containsKey("CORRECTFORMAT"));
		assertTrue(fullmakter.get(ListName.CATEGORY).containsValue("Trim av description"));
	}

	*/
}
