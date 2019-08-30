package no.nav.pdl.fullmakt.app.common;

import no.nav.pdl.fullmakt.app.AppStarter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = AppStarter.class)
@EnableJpaRepositories(basePackageClasses = AppStarter.class)
@Configuration
public class JpaConfig {

}
