package no.nav.pdl.fullmakt.app.fullmakt;

import lombok.extern.slf4j.Slf4j;
import no.nav.pdl.fullmakt.app.common.exceptions.FullmaktNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FullmaktService {

	private FullmaktRepository repository;


	public FullmaktService(FullmaktRepository repository) {
		this.repository = repository;
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


	public Fullmakt save(Fullmakt request) {
		return repository.save(request);
	}

	public Fullmakt update(Fullmakt request) {
		return repository.save(request);
	}

	public void delete(Long fullmaktId) {
		Optional<Fullmakt> toDelete = repository.findByFullmaktId(fullmaktId);
		if (toDelete.isPresent()) {
			repository.deleteByFullmaktId(fullmaktId);
		} else {
			log.error("Cannot find a fullmakt to delete with fullmaktId={} ", fullmaktId);
			throw new IllegalArgumentException(
					String.format("Cannot find a fullmakt to delete with fullmaktId=%s ", fullmaktId));
		}
	}
}