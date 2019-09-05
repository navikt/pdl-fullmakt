package no.nav.pdl.fullmakt.app.fullmaktEndringslogg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullmaktEndringsloggRepository  extends JpaRepository<FullmaktEndringslogg
        , Integer>{
}

