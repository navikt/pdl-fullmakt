package no.nav.pdl.fullmakt.app.fullmakt;


import static no.nav.pdl.fullmakt.app.fullmakt.FullmaktService.fullmakter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
		classes = {IntegrationTestConfig.class, AppStarter.class})
@ActiveProfiles("test")
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
public class FullmaktServiceIT {

	@Autowired
	private FullmaktService service;

	@Autowired
	private FullmaktRepository repository;

	@ClassRule
	public static PostgresTestContainer postgreSQLContainer = PostgresTestContainer.getInstance();

	@Before
	public void setUp() {
		repository.deleteAll();
	}

	@After
	public void cleanUp() {
		repository.deleteAll();
	}

	@Test
	public void save_shouldSaveNewFullmakt() {
		service.save(createListOfOneRequest());

		assertThat(repository.findAll().size(), is(1));
		assertTrue(repository.findByListAndCode(ListName.PRODUCER, "TEST_CODE").isPresent());
		assertThat(fullmakter.get(ListName.PRODUCER).size(), is(1));
		assertFalse(fullmakter.get(ListName.PRODUCER).get("TEST_CODE").isEmpty());
	}

	@Test
	public void update_shouldUpdateFullmakt() {
		service.save(createListOfOneRequest());

		List<FullmaktRequest> updatedRequest = createListOfOneRequest(ListName.PRODUCER, "TEST_CODE", "Updated fullmakt");
		service.update(updatedRequest);

		assertThat(fullmakter.get(ListName.PRODUCER).get("TEST_CODE"), is(updatedRequest.get(0).getDescription()));
		assertThat(repository.findByListAndCode(ListName.PRODUCER, "TEST_CODE").get().getDescription(), is(updatedRequest.get(0)
				.getDescription()));
	}

	@Test
	public void delete_shouldDeleteFullmakt() {
		List<FullmaktRequest> request = createListOfOneRequest();
		service.save(request);
		assertThat(repository.findAll().size(), is(1));
		assertThat(fullmakter.get(ListName.PRODUCER).size(), is(1));

		service.delete(ListName.PRODUCER, "TEST_CODE");

		assertThat(repository.findAll().size(), is(0));
		assertFalse(repository.findByListAndCode(ListName.PRODUCER, "TEST_CODE").isPresent());
		assertThat(fullmakter.get(ListName.PRODUCER).size(), is(0));
		assertNull(fullmakter.get(ListName.PRODUCER).get("TEST_CODE"));
	}

	@Test
	public void validateRequests_shouldValidateRequests() {
		List<FullmaktRequest> requests = List.of(
				createOneRequest(ListName.PRODUCER, "CODE_1", "Description"),
				createOneRequest(ListName.PRODUCER, "code_2 ", "Description"),
				createOneRequest(ListName.PRODUCER, " Code_3 ", "Description "));

		service.validateRequests(requests, false);
	}

	private FullmaktRequest createOneRequest(ListName listName, String code, String description) {
		return FullmaktRequest.builder()
				.list(listName)
				.code(code)
				.description(description)
				.build();
	}

	private List<FullmaktRequest> createListOfOneRequest(ListName listName, String code, String description) {
		return List.of(FullmaktRequest.builder()
				.list(listName)
				.code(code)
				.description(description)
				.build());
	}

	private List<FullmaktRequest> createListOfOneRequest() {
		return createListOfOneRequest(ListName.PRODUCER, "TEST_CODE", "Test description");
	}

}

