package no.nav.pdl.fullmakt.app.common.security;

import no.nav.freg.security.oidc.auth.common.HttpSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public HttpSecurityConfigurer httpSecurityConfigurer() {
        return new HttpSecurityConfigurer() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .exceptionHandling()
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .and()
                        .csrf().disable()
                        .cors().disable()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .headers().contentSecurityPolicy("script-src 'self'");
            }
        };
    }
}
