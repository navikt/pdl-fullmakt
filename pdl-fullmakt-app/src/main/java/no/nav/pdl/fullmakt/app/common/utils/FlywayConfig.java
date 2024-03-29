package no.nav.pdl.fullmakt.app.common.utils;

import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.lease.SecretLeaseContainer;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "spring.cloud.vault.enabled", matchIfMissing = true)
public class FlywayConfig {

    @Bean
    public FlywayConfigurationCustomizer flywayConfigurationCustomizer(
            SecretLeaseContainer secretLeaseContainer,
            VaultOperations vaultOperations,
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.cloud.vault.database.role}") String userRole,
            @Value("${spring.cloud.vault.database.backend}") String backend
    ) {
        return configuration -> {
            String adminRole = userRole.replace("-user", "-admin");
            val secretPath = format("%s/creds/%s", backend, adminRole);

            val vaultResponse = vaultOperations.read(secretPath);
            val username = vaultResponse.getData().get("username").toString();
            val password = vaultResponse.getData().get("password").toString();

            log.info("Vault: Flyway configured with credentials from Vault. Credential path: {}", secretPath);

            configuration
                    .dataSource(url, username, password)
                    .initSql(String.format("SET ROLE \"%s\"", adminRole));
        };
    }
}
