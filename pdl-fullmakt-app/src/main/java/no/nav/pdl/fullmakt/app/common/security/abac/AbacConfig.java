package no.nav.pdl.fullmakt.app.common.security.abac;

import no.nav.abac.xacml.NavAttributter;
import no.nav.freg.abac.core.annotation.attribute.AbacAttributeLocator;
import no.nav.freg.abac.core.annotation.attribute.ResolvingAbacAttributeLocator;
import no.nav.freg.abac.spring.config.AbacRestTemplateConfig;
import no.nav.freg.security.oidc.auth.common.OidcTokenAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({no.nav.freg.abac.spring.config.AbacConfig.class, AbacRestTemplateConfig.class})
public class AbacConfig {

    @Bean
    Set<String> abacDefaultResources() {
        Set<String> values = new HashSet<>();
        values.add(NavAttributter.RESOURCE_FELLES_DOMENE);
        return values;
    }

    @Bean
    Set<String> abacDefaultEnvironment() {
        Set<String> values = new HashSet<>();
        values.add(NavAttributter.ENVIRONMENT_FELLES_PEP_ID);
        values.add(NavAttributter.ENVIRONMENT_FELLES_CONSUMER_OIDC_TOKEN_BODY);
        values.add(NavAttributter.ENVIRONMENT_FELLES_OIDC_TOKEN_BODY);
        return values;
    }

    @Bean
    AbacAttributeLocator fellesDomeneLocator() {
        return new ResolvingAbacAttributeLocator(NavAttributter.RESOURCE_FELLES_DOMENE, () -> "navperson");
    }

    @Bean
    AbacAttributeLocator pepIdLocator() {
        return new ResolvingAbacAttributeLocator(NavAttributter.ENVIRONMENT_FELLES_PEP_ID, () -> "pdl-fullmakt");
    }

    @Bean
    AbacAttributeLocator tokenBodyLocator() {
        return new ResolvingAbacAttributeLocator(NavAttributter.ENVIRONMENT_FELLES_CONSUMER_OIDC_TOKEN_BODY, () ->
                ((OidcTokenAuthentication) SecurityContextHolder.getContext().getAuthentication()).getConsumerTokenBody());
    }

    @Bean
    AbacAttributeLocator idTokenBodyLocator() {
        return new ResolvingAbacAttributeLocator(NavAttributter.ENVIRONMENT_FELLES_OIDC_TOKEN_BODY, () ->
                ((OidcTokenAuthentication) SecurityContextHolder.getContext().getAuthentication()).getIdTokenBody());
    }
}
