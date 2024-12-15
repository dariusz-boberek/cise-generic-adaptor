package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonDTO;
import eu.cise.adaptor.quarkus.nodeapi.CommonNodeApiTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@QuarkusTest
class ContactPersonApiTest extends CommonNodeApiTest {


    @Test
    void test_contactPersonsLocalUuidGet() {
        UUID uuid = CommonNodeApiTest.uuid;

        ContactPersonDTO result = serviceHandlerManagementAPIAdapter.getContactPersonApi().contactPersonsLocalUuidGet(uuid);

        assertEquals(CommonNodeApiTest.contactPersonDTO.getName(), result.getName());

        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        wireMockServer.verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        wireMockServer.verify(getRequestedFor(urlEqualTo("/contact-persons/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }

    @Test
    void test_contactPersonsLocalUuidDelete() {
        UUID uuid = CommonNodeApiTest.uuid;

        serviceHandlerManagementAPIAdapter.getContactPersonApi().contactPersonsLocalUuidDelete(uuid);

        verify(keycloakLoginRESTClient).retrieveToken(username, password, "password");

        wireMockServer.verify(postRequestedFor(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                .withRequestBody(equalTo("password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) + "&grant_type=password&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8))));

        wireMockServer.verify(deleteRequestedFor(urlEqualTo("/contact-persons/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + CommonNodeApiTest.accessToken)));
    }
}
