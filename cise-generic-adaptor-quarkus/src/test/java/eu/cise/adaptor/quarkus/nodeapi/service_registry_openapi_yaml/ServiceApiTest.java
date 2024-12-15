package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceOperation;
import eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceRole;
import eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceStatus;
import eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceType;
import eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByService;
import eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.LongDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOServiceDTO;
import eu.cise.adaptor.quarkus.nodeapi.CommonNodeApiTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@QuarkusTest
class ServiceApiTest extends CommonNodeApiTest {

    @Test
    void test_servicesLocalStatsCountGet() {

        LongDTO result = serviceHandlerManagementAPIAdapter.getServiceApi().servicesLocalStatsCountGet();

        assertEquals(CommonNodeApiTest.numberOfServices, result.getValue());

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(getRequestedFor(urlEqualTo("/services/local/stats/count"))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }

    @Test
    void test_servicesLocalUuidDelete() {

        serviceHandlerManagementAPIAdapter.getServiceApi().servicesLocalUuidDelete(CommonNodeApiTest.uuid);

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(deleteRequestedFor(urlEqualTo("/services/local/" + CommonNodeApiTest.uuid))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }

    @Test
    void test_servicesGet() {

        Integer limit = 100;
        Integer offset = 0;
        String serviceId = null;
        String serviceOperation = ServiceOperation.PUSH.value();
        String serviceRole = ServiceRole.CONSUMER.value();
        List<String> serviceStatusList = List.of(ServiceStatus.ONLINE.value());
        String serviceType = ServiceType.VESSEL_SERVICE.value();
        Boolean smart = null;
        String sortBy = SortByService.SERVICE_ID.value();
        String sortOrder = SortOrder.ASC.getOrderByExpression();

        ResponseDTOServiceDTO result = serviceHandlerManagementAPIAdapter.getServiceApi().servicesGet(
                limit,
                offset,
                serviceId,
                serviceOperation,
                serviceRole,
                serviceStatusList,
                serviceType,
                smart,
                sortBy,
                sortOrder
        );

        assertNotNull(result);

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(getRequestedFor(urlPathMatching("/services.*"))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }

}