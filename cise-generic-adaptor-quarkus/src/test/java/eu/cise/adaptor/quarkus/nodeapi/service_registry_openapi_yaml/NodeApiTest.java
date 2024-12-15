package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeDTO;
import eu.cise.adaptor.quarkus.nodeapi.CommonNodeApiTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@QuarkusTest
class NodeApiTest extends CommonNodeApiTest {

    @Test
    void test_nodeGet() {

        NodeDTO result = serviceHandlerManagementAPIAdapter.getNodeApi().nodeGet();

        assertNotNull(result);

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(getRequestedFor(urlEqualTo("/node"))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }

    @Test
    void test_nodesRemotePost() {

        serviceHandlerManagementAPIAdapter.getNodeApi().nodesRemotePost(new NodeBaseDTO());

        // Verify that the request for the tokens was done using username and password provided by configuration
        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        // Verify that the token is asked to Keycloak server using username and password provided by configuration
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                        .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        // Verify that the rest call to the services was done using the token received previously
        wireMockServer.
                verify(postRequestedFor(urlEqualTo("/nodes/remote"))
                        .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }
}