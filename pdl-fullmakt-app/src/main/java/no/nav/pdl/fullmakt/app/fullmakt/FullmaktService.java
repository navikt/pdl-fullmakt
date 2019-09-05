package no.nav.pdl.fullmakt.app.fullmakt;

import lombok.extern.slf4j.Slf4j;
import no.nav.pdl.fullmakt.app.common.exceptions.FullmaktNotFoundException;
import no.nav.pdl.fullmakt.app.fullmaktEndringslogg.FullmaktEndringslogg;
import no.nav.pdl.fullmakt.app.fullmaktEndringslogg.FullmaktEndringsloggRepository;
import org.codehaus.jackson.map.ObjectMapper;
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
		return repository.findAllByFullmaktsgiver(fullmaktsgiver);
	}


	public  List<Fullmakt> getFullmaktForFullmektig( String fullmektig) {
		return repository.findAllByFullmektig(fullmektig);
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
		fullmaktEndringslogg.save( mapper.convertValue(fullmakt, FullmaktEndringslogg.class));

		return fullmakt;
	}

	@Transactional
	public Fullmakt update(Fullmakt request) {
		 ObjectMapper mapper = new ObjectMapper();
		 fullmaktEndringslogg.save( mapper.convertValue(request, FullmaktEndringslogg.class));
		return repository.save(request);
	}

	public void delete(Long fullmaktId) {
		Optional<Fullmakt> toDelete = repository.findByFullmaktId(fullmaktId);
		if (toDelete.isPresent()) {
			toDelete.get().setOpphoert(true);
			ObjectMapper mapper = new ObjectMapper();
			fullmaktEndringslogg.save(mapper.convertValue(toDelete, FullmaktEndringslogg.class));
			repository.deleteByFullmaktId(fullmaktId);
		} else {
			log.error("Cannot find a fullmakt to delete with fullmaktId={} ", fullmaktId);
			throw new IllegalArgumentException(
					String.format("Cannot find a fullmakt to delete with fullmaktId=%s ", fullmaktId));
		}
	}
}