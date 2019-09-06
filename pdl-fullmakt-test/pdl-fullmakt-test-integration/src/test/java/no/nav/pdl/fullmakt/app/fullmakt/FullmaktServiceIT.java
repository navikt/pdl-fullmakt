package no.nav.pdl.fullmakt.app.fullmakt;


import no.nav.pdl.fullmakt.app.AppStarter;
import no.nav.pdl.fullmakt.app.IntegrationTestConfig;
import no.nav.pdl.fullmakt.app.PostgresTestContainer;
import org.assertj.core.util.DateUtil;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		service.save(createOneRequest());
		assertThat(repository.findAll().size(), is(1));

	}


	@Test
	public void delete_shouldDeleteFullmakt() {
		Fullmakt request = createOneRequest();
		service.save(request);
		assertThat(repository.findAll().size(), is(1));
		service.delete((long) 1);

		assertThat(repository.findAll().size(), is(0));
	}


	private Fullmakt createOneRequest () {
		return Fullmakt.builder()
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

	}


}

