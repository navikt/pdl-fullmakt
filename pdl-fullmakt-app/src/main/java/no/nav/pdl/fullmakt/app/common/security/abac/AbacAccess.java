package no.nav.pdl.fullmakt.app.common.security.abac;

import lombok.val;
import no.nav.freg.abac.core.annotation.context.AbacContext;
import no.nav.freg.abac.core.dto.response.Decision;
import no.nav.freg.abac.core.service.AbacService;
import no.nav.pdl.fullmakt.app.common.exception.UnauthorizedException;
import no.nav.pdl.fullmakt.app.common.exception.UnrecoverableException;
import no.nav.person.utils.audit.abac.AbacAttributeAppender;
import no.nav.person.utils.audit.context.AuditContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static no.nav.abac.xacml.NavAttributter.RESOURCE_FELLES_PERSON_FNR;
import static no.nav.abac.xacml.NavAttributter.RESOURCE_FELLES_RESOURCE_TYPE;
import static no.nav.pdl.fullmakt.app.common.security.abac.AbacUtils.OPPYSNING_ATTRIBUTE_MAP;
import static no.nav.person.utils.audit.AuditConstants.RESOURCE_ID;
import static no.nav.person.utils.audit.AuditConstants.RESOURCE_TYPE;

@Component
public class AbacAccess {

    @Autowired
    private AbacAttributeAppender abacAttributeAppender;

    @Autowired
    private AbacService abacService;

    @Autowired
    private AbacContext abacContext;

    @Autowired
    private AuditContext auditContext;

    public void evaluate(String ident) {
        abacContext.getRequest().getResources().removeIf(a -> RESOURCE_FELLES_PERSON_FNR.equals(a.getAttributeId()));
        abacContext.getRequest().resource(RESOURCE_FELLES_PERSON_FNR, ident);
        auditContext.appendAuditAttributePair(RESOURCE_ID, ident);

        val response = abacService.evaluate(abacContext.getRequest());
        abacAttributeAppender.append(response);
        if (response.getDecision() != Decision.PERMIT) {
            throw new UnauthorizedException("Not permitted by ABAC.");
        }
    }

    public void evaluate(String ident, String opplysningstypeSimpleName) {
        if (isNull(opplysningstypeSimpleName) || !OPPYSNING_ATTRIBUTE_MAP.containsKey(opplysningstypeSimpleName)) {
            throw new UnrecoverableException(format("Could not find ABAC resource type for opplysningstype=%s", opplysningstypeSimpleName));
        }
        val navpersonResourceType = OPPYSNING_ATTRIBUTE_MAP.get(opplysningstypeSimpleName);

        abacContext.getRequest().getResources().removeIf(a -> RESOURCE_FELLES_RESOURCE_TYPE.equals(a.getAttributeId()));
        abacContext.getRequest().resource(RESOURCE_FELLES_RESOURCE_TYPE, navpersonResourceType);
        auditContext.appendAuditAttributePair(RESOURCE_TYPE, navpersonResourceType);
        evaluate(ident);
    }

    public void evalute(String ident, no.nav.tjenester.person.pdl.endring.v1.Type opplysningstype) {
        val opplysningstypeSimpleName = isNull(opplysningstype) ? null : opplysningstype.getType().getSimpleName();
        evaluate(ident, opplysningstypeSimpleName);
    }
}
