package no.nav.pdl.fullmakt.app.common;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
public class CommonConfig {


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	/**
	 * Make sure spring uses the defaultRegistry
	 */
	@Bean
	public CollectorRegistry collectorRegistry() {
		DefaultExports.initialize();
		return CollectorRegistry.defaultRegistry;
	}
}
