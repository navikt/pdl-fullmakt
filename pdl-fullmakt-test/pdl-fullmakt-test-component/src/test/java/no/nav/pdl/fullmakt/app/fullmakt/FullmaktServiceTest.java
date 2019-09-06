package no.nav.pdl.fullmakt.app.fullmakt;

import no.nav.pdl.fullmakt.app.fullmaktEndringslogg.FullmaktEndringsloggRepository;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class FullmaktServiceTest {

	@Mock
	private FullmaktRepository repository;

    @Mock
    private FullmaktEndringsloggRepository fullmaktEndringsloggRepository;

	@InjectMocks
	private FullmaktService service;

	@Test
	public void save_shouldCreateRequest() {
        // when(fullmaktEndringsloggRepository.save(ArgumentMatchers.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

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
		// verify(repository, times(1)).saveAll(anyList());
		// assertThat( repository.findByFullmaktId(1L).get().getFullmaktId(), is(1L));
	}


}
