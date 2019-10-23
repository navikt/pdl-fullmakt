package no.nav.pdl.fullmakt.app.common.security.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
public class UserAuditorAware implements AuditorAware<String> {

    @Value("${APP_NAME}")
    private String app;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(auth) && nonNull(auth.getName())) {
            return Optional.of(auth.getName());
        }

        return Optional.of(app);
    }
}
