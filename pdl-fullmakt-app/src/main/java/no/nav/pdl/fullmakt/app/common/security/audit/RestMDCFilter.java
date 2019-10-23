package no.nav.pdl.fullmakt.app.common.security.audit;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static no.nav.pdl.fullmakt.app.common.Globals.NAV_CALL_ID;
import static no.nav.pdl.fullmakt.app.common.rest.api.DefaultHeaderAdvice.SYSTEM_KILDE;
import static org.slf4j.MDC.remove;

@Slf4j
@Component
public class RestMDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (Strings.isNullOrEmpty(request.getHeader(NAV_CALL_ID))) {
                MDC.put(NAV_CALL_ID, UUID.randomUUID().toString());
            } else {
                MDC.put(NAV_CALL_ID, request.getHeader(NAV_CALL_ID));
            }
            filterChain.doFilter(request, response);
        } finally {
            remove(NAV_CALL_ID);
            remove(SYSTEM_KILDE);
        }
    }
}
