package eu.cise.adaptor.quarkus.nodeapi.subscription_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.ResponseDTOSubscriptionDTO;
import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.SubscriptionUpdateDTO;
import eu.cise.adaptor.quarkus.nodeapi.CommonNodeApiTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@QuarkusTest
class SubscriptionApiTest extends CommonNodeApiTest {

    @Test
    void test_getSubscriptionApi() {

        LocalDate expireDateFrom = null;
        LocalDate expireDateTo = null;
        Integer limit = null;
        Integer offset = null;
        String providerServiceId = null;
        Boolean smart = null;
        String sortBy = null;
        String sortOrder = null;
        String status = null;
        String subscriberParticipantId = null;
        String subscriberServiceId = null;

        ResponseDTOSubscriptionDTO result = serviceHandlerManagementAPIAdapter.getSubscriptionApi().subscriptionsGet(
                expireDateFrom,
                 expireDateTo,
                 limit,
                 offset,
                 providerServiceId,
                 smart,
                 sortBy,
                 sortOrder,
                 status,
                 subscriberParticipantId,
                 subscriberServiceId);

        assertNotNull(result);

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(getRequestedFor(urlEqualTo("/subscriptions"))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }

    @Test
    void test_subscriptionsUuidPut() {

        serviceHandlerManagementAPIAdapter.getSubscriptionApi().subscriptionsUuidPut(CommonNodeApiTest.uuid.toString(), new SubscriptionUpdateDTO());

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(putRequestedFor(urlEqualTo("/subscriptions/" + CommonNodeApiTest.uuid))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }
}