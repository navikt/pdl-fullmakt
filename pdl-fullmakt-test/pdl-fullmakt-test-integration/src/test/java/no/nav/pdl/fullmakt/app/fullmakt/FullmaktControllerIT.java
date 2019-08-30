package no.nav.pdl.fullmakt.app.fullmakt;

import no.nav.pdl.fullmakt.app.AppStarter;
import no.nav.pdl.fullmakt.app.IntegrationTestConfig;
import no.nav.pdl.fullmakt.app.PostgresTestContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static no.nav.pdl.fullmakt.app.fullmakt.FullmaktService.fullmakter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = {IntegrationTestConfig.class, AppStarter.class})
@ActiveProfiles("test")
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
public class FullmaktControllerIT {

	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	private FullmaktService service;

	@Autowired
	private FullmaktRepository repository;

	@ClassRule
	public static PostgresTestContainer postgreSQLContainer = PostgresTestContainer.getInstance();

	@Before
	public void setUp() {
		service.refreshCache();
		fullmakter.get(ListName.PRODUCER).put("TEST_CODE", "Test description");
	}

	@After
	public void cleanUp() {
		repository.deleteAll();
	}

	@Test
	public void findAll_shouldReturnOneFullmakts() {
		ResponseEntity<Map> responseEntity = restTemplate.exchange(
				"/fullmakt", HttpMethod.GET, HttpEntity.EMPTY, Map.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getBody().size(), is(4));

		Arrays.stream(ListName.values())
				.forEach(listName -> assertThat(responseEntity.getBody()
						.get(listName.toString()), is(fullmakter.get(listName))));
	}

	@Test
	public void getFullmaktByListName_shouldReturnCodesAndDescriptionForListName() {
		String url = "/fullmakt/PRODUCER";

		ResponseEntity<Map> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, HttpEntity.EMPTY, Map.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getBody(), is(fullmakter.get(ListName.PRODUCER)));
	}

	@Test
	public void getDescriptionByListNameAndCode_shouldReturnDescriptionForCodeAndListName() {
		String url = "/fullmakt/PRODUCER/TEST_CODE";

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getBody(), is(fullmakter.get(ListName.PRODUCER).get("TEST_CODE")));
	}

	@Test
	public void save_shouldSaveNewFullmakt() {
		String code = "SAVE_CODE";
		List<FullmaktRequest> requests = createRequest(ListName.PRODUCER, code, "Test description");
		assertNull(fullmakter.get(ListName.PRODUCER).get(code));

		ResponseEntity<List<Fullmakt>> responseEntity = restTemplate.exchange(
				"/fullmakt", HttpMethod.POST, new HttpEntity<>(requests), new ParameterizedTypeReference<List<Fullmakt>>() {
				});

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		assertFalse(fullmakter.get(ListName.PRODUCER).get(code).isEmpty());
		assertThat(responseEntity.getBody().get(0).getDescription(), is(fullmakter.get(ListName.PRODUCER).get(code)));
	}

	@Test
	public void save_shouldSave20Fullmakt() {
		List<FullmaktRequest> requests = createNrOfRequests("shouldSave20Fullmakts", 20);

		ResponseEntity<List<Fullmakt>> responseEntity = restTemplate.exchange(
				"/fullmakt", HttpMethod.POST, new HttpEntity<>(requests), new ParameterizedTypeReference<List<Fullmakt>>() {
				});

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(fullmakter.get(ListName.SYSTEM).size(), is(20));
	}

	@Test
	public void update_shouldUpdateOneFullmakt() {
		String code = "UPDATE_CODE";
		service.save(createRequest(ListName.PRODUCER, code, "Test description"));

		List<FullmaktRequest> updatedFullmakts = createRequest(ListName.PRODUCER, code, "Updated fullmakter");

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/fullmakt", HttpMethod.PUT, new HttpEntity<>(updatedFullmakts), String.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.ACCEPTED));
		assertThat(fullmakter.get(ListName.PRODUCER).get(code), is(updatedFullmakts.get(0).getDescription()));
	}

	@Test
	public void update_shouldUpdate20Fullmakts() {
		List<FullmaktRequest> requests = createNrOfRequests("shouldUpdate20Fullmakts", 20);
		restTemplate.exchange(
				"/fullmakt", HttpMethod.POST, new HttpEntity<>(requests), new ParameterizedTypeReference<List<Fullmakt>>() {
				});

		requests.forEach(request -> request.setDescription("  Updated fullmakter  "));
		ResponseEntity<List<Fullmakt>> responseEntity = restTemplate.exchange(
				"/fullmakt", HttpMethod.PUT, new HttpEntity<>(requests), new ParameterizedTypeReference<List<Fullmakt>>() {
				});

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.ACCEPTED));
		assertThat(fullmakter.get(ListName.SYSTEM).size(), is(20));
		fullmakter.get(ListName.SYSTEM);
		Collection<String> descriptionList = fullmakter.get(ListName.SYSTEM).values();
		descriptionList.forEach(description -> assertThat(description, is("Updated fullmakter")));
	}


	@Test
	public void delete_shouldDeleteFullmakt() {
		String code = "DELETE_CODE";
		List<FullmaktRequest> requests = createRequest(ListName.PRODUCER, code, "Test description");
		service.save(requests);
		assertNotNull(fullmakter.get(ListName.PRODUCER).get(code));

		String url = "/fullmakt/PRODUCER/DELETE_CODE";

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertNull(fullmakter.get(ListName.PRODUCER).get(code));
	}

	private List<FullmaktRequest> createNrOfRequests(String code, int nrOfRequests) {
		return IntStream.rangeClosed(1, nrOfRequests)
				.mapToObj(i -> createOneRequest(ListName.SYSTEM, code + "_nr_" + i, "Test description"))
				.collect(Collectors.toList());

	}

	private FullmaktRequest createOneRequest(ListName listName, String code, String description) {
		return FullmaktRequest.builder()
				.list(listName)
				.code(code)
				.description(description)
				.build();
	}

	private List<FullmaktRequest> createRequest(ListName listName, String code, String description) {
		return List.of(createOneRequest(listName, code, description));
	}

}
