package no.nav.pdl.fullmakt.app.fullmakt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FullmaktRepository extends JpaRepository<Fullmakt, Integer> {


	List<Fullmakt> findAllByFullmaktsgiver(@Param("fullmaktsgiver") String fullmaktsgiver);
	List<Fullmakt> findAllByFullmektig(@Param("fullmektig") String fullmektig);


	List<Fullmakt> findAll();

	void deleteByFullmaktId(@Param("fullmaktId") Long fullmaktId);
	Optional<Fullmakt> findByFullmaktId(@Param("fullmaktId") Long fullmaktId);
}
