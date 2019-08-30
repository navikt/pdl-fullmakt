package no.nav.pdl.fullmakt.app.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.DefaultExports;
import no.nav.pdl.fullmakt.app.common.auditing.AuditorAwareImpl;
import no.nav.pdl.fullmakt.app.common.utils.JsonUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CommonConfig {

	@Primary
	@Bean
	public ObjectMapper objectMapper() {
		return JsonUtils.getObjectMapper();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(objectMapper());
		return jsonConverter;
	}


	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
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
