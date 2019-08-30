package no.nav.pdl.fullmakt.app.fullmakt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FullmaktRepository extends JpaRepository<Fullmakt, Integer> {
	List<Fullmakt> findAllByList(@Param("list") String listName);

	Optional<Fullmakt> findByListAndCode(@Param("list") ListName list, @Param("code") String code);

}
