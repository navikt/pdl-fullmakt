package no.nav.pdl.fullmakt.app;

import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.SocketUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {IntegrationTestConfig.class, AppStarter.class})
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
public abstract class IntegrationTestBase {


    private static final int wiremockport = SocketUtils.findAvailableTcpPort();

    @ClassRule
    public static PostgresTestContainer postgreSQLContainer = PostgresTestContainer.getInstance();
    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(wiremockport) {
        @Override
        protected void before() {
            System.setProperty("wiremock.server.port", String.valueOf(wiremockport));
        }
    };
    @Autowired
    protected TransactionTemplate transactionTemplate;

    protected void policyStubbing() {
        wiremock.stubFor(get(urlPathEqualTo("/api/fullmakt"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(ContentTypeHeader.KEY, "application/json")
                        .withBody("{}")
                ));
    }
}
