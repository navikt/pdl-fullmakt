package no.nav.pdl.fullmakt.app.fullmakt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.pdl.fullmakt.app.common.exceptions.FullmaktNotFoundException;
import no.nav.pdl.fullmakt.app.fullmaktEndringslogg.FullmaktEndringslogg;
import no.nav.pdl.fullmakt.app.fullmaktEndringslogg.FullmaktEndringsloggRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FullmaktService {

	private FullmaktRepository repository;

	private FullmaktEndringsloggRepository fullmaktEndringslogg;

	public FullmaktService(FullmaktRepository repository, FullmaktEndringsloggRepository fullmaktEndringslogg) {
		this.repository = repository;
		this.fullmaktEndringslogg = fullmaktEndringslogg;
	}


	public  List<Fullmakt> getAllFullmakt() {
		return repository.findAll();
	}


	public  List<Fullmakt> getFullmaktForFullmaktsgiver( String fullmaktsgiver) {
		return repository.findAllByFullmaktsgiverOrderByFullmaktIdDesc(fullmaktsgiver);
	}


	public  List<Fullmakt> getFullmaktForFullmektig( String fullmektig) {
		return repository.findAllByFullmektigOrderByFullmaktIdDesc(fullmektig);
	}


	public  Fullmakt getFullmakt(Long fullmaktId) {
		Optional<Fullmakt> optionalFullmakt = repository.findByFullmaktId(fullmaktId);
		if (optionalFullmakt.isPresent()) {
			return optionalFullmakt.get();
		}

		log.error("Cannot find fullmakt with fullmaktId={}", fullmaktId);
		throw new FullmaktNotFoundException(String.format(
				"Cannot find fullmakt with fullmaktId=%s", fullmaktId));
	}

	@Transactional
	public Fullmakt save(Fullmakt request) {
		Fullmakt fullmakt = repository.save(request);
		ObjectMapper mapper = new ObjectMapper();
		fullmaktEndringslogg.save(mapper.convertValue(fullmakt, FullmaktEndringslogg.class));

		return fullmakt;
	}


	@Transactional
	public void delete(Long fullmaktId) {
		Optional<Fullmakt> toDelete = repository.findByFullmaktId(fullmaktId);
		if (toDelete.isPresent()) {
			toDelete.get().setOpphoert(true);
			FullmaktEndringslogg fullmaktEndringslog = FullmaktEndringslogg.builder()
					.fullmaktId(toDelete.get().getFullmaktId())
					.ednretAv(toDelete.get().getEdnretAv())
					.endret(toDelete.get().getEndret())
					.registrert(toDelete.get().getRegistrert())
					.registrertAv(toDelete.get().getRegistrertAv())
					.opphoert(true)
					.fullmaktsgiver(toDelete.get().getFullmaktsgiver())
					.fullmektig(toDelete.get().getFullmektig())
					.omraade(toDelete.get().getOmraade())
					.gyldigFraOgMed(toDelete.get().getGyldigFraOgMed())
					.gyldigTilOgMed(toDelete.get().getGyldigTilOgMed())
					.build();

			fullmaktEndringslogg.save(fullmaktEndringslog);
			repository.deleteByFullmaktId(fullmaktId);
		} else {
			log.error("Cannot find a fullmakt to delete with fullmaktId={} ", fullmaktId);
			throw new IllegalArgumentException(
					String.format("Cannot find a fullmakt to delete with fullmaktId=%s ", fullmaktId));
		}
	}
}