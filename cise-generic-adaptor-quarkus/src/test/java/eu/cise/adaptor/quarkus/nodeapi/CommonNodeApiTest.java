package eu.cise.adaptor.quarkus.nodeapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import eu.cise.adaptor.core.servicehandler.port.out.ServiceHandlerManagementAPIPort;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.AuthorityDetailsDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.LongDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOServiceDTO;
import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.ResponseDTOSubscriptionDTO;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.auth.KeycloakTokenHandler;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.model.KeycloakTokenDTO;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client.KeycloakLoginRESTClient;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class CommonNodeApiTest {

    public static final String accessToken = "access_token";
    public static final long numberOfServices = 28L;

    public static final ParticipantDTO participantDTO = new ParticipantDTO();

    static {
        participantDTO.setName("Alice");
    }

    public static final UUID uuid = UUID.randomUUID();
    public static final ContactPersonDTO contactPersonDTO = new ContactPersonDTO();

    static {
        contactPersonDTO.setName("James");
    }

    @ConfigProperty(name = "cisenode.api.credentials.username")
    protected String username;

    @ConfigProperty(name = "cisenode.api.credentials.password")
    protected String password;

    @InjectSpy
    @RestClient
    protected KeycloakLoginRESTClient keycloakLoginRESTClient;

    @Inject
    protected ServiceHandlerManagementAPIPort serviceHandlerManagementAPIAdapter;

    protected static WireMockServer wireMockServer;

    @BeforeAll
    public static void init() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }

    @AfterAll
    public static void cleanup() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @BeforeEach
    void initTests() {
        KeycloakTokenHandler.setToken(null);
        if (wireMockServer == null) {
            wireMockServer = createAndStartWiremockServer();
        }
    }

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-file")
    String serverKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-password")
    String serverKeystorePassword;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-file")
    String clientKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-password")
    String clientKeystorePassword;

    protected WireMockServer createAndStartWiremockServer() {

        Path keystorePath = null;
        Path trustStorePath = null;

        try {
            // client keystore generated using the following command:
            // keytool -genkeypair -storepass password -keyalg RSA -keysize 2048 -dname "CN=client" -alias client -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore client.keystore
            // server keystore generated using the following command:
            // keytool -genkeypair -storepass password -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore server.keystore
            keystorePath = Paths.get(CommonNodeApiTest.class.getClassLoader().getResource(serverKeystore).toURI());
            trustStorePath = Paths.get(CommonNodeApiTest.class.getClassLoader().getResource(clientKeystore).toURI());
            if (!Files.exists(keystorePath) || !Files.exists(trustStorePath)) {
                throw new RuntimeException("Keystore or Truststore file does not exist.");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
                .httpsPort(8200)
                .keystorePath(keystorePath.toString())
                .keystorePassword(serverKeystorePassword)
                .keyManagerPassword(serverKeystorePassword)
                .needClientAuth(true)
                .trustStorePath(trustStorePath.toString())
                .trustStorePassword(clientKeystorePassword)
                .notifier(new Slf4jNotifier(true)));

        wireMockServer.stubFor(get(urlPathMatching("/.*"))
                .withHeader(HttpHeaders.AUTHORIZATION, notMatching("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_UNAUTHORIZED)));

        wireMockServer.stubFor(put(urlPathMatching("/.*"))
                .withHeader(HttpHeaders.AUTHORIZATION, notMatching("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_UNAUTHORIZED)));

        wireMockServer.stubFor(delete(urlPathMatching("/.*"))
                .withHeader(HttpHeaders.AUTHORIZATION, notMatching("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_UNAUTHORIZED)));

        wireMockServer.stubFor(post(urlPathMatching("/.*"))
                .withHeader(HttpHeaders.AUTHORIZATION, notMatching("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_UNAUTHORIZED)));

        // Istio token request
        wireMockServer.stubFor(post(urlEqualTo("/auth/realms/cise/protocol/openid-connect/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getToken())));

        // ServiceApi#servicesLocalStatsCountGet
        wireMockServer.stubFor(get(urlEqualTo("/services/local/stats/count"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getNumberOfServices())));

        //  ServiceApi#servicesGet
        wireMockServer.stubFor(get(urlPathEqualTo("/services"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("serviceOperation", equalTo("Push"))
                .withQueryParam("serviceRole", equalTo("Consumer"))
                .withQueryParam("serviceStatus", equalTo("Online"))
                .withQueryParam("serviceType", equalTo("VesselService"))
                .withQueryParam("sortBy", equalTo("serviceId"))
                .withQueryParam("sortOrder", equalTo("asc"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getResponseDTOServicesDTO())));

        //  ServiceApi#servicesLocalUuidDelete
        wireMockServer.stubFor(delete(urlEqualTo("/services/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)));


        // AuthorityApi#authoritiesLocalUuidGet
        wireMockServer.stubFor(get(urlEqualTo("/authorities/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getAuthorityDetailsDTO())));

        // AuthorityApi#authoritiesLocalUuidPut
        wireMockServer.stubFor(put(urlEqualTo("/authorities/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)));

        // NodeApi#nodeGet
        wireMockServer.stubFor(get(urlEqualTo("/node"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getNodeConfigurationDTO())));

        // NodeApi#nodesRemotePost
        wireMockServer.stubFor(post(urlEqualTo("/nodes/remote"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)));

        // ContactPersonApi#contactPersonsLocalUuidGet
        wireMockServer.stubFor(get(urlEqualTo("/contact-persons/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getContactPersonDTO())));

        // ContactPersonApi#contactPersonsLocalUuidDelete
        wireMockServer.stubFor(delete(urlEqualTo("/contact-persons/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(204)));

        // ParticipantApi#participantsLocalUuidGet
        wireMockServer.stubFor(get(urlEqualTo("/participants/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getParticipantDTO())));

        // ParticipantApi#participantsLocalUuidDelete
        wireMockServer.stubFor(delete(urlEqualTo("/participants/local/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(204)));

        // Subscriptions#subscriptionsGet
        wireMockServer.stubFor(get(urlEqualTo("/subscriptions"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getResponseDTOSubscriptionDTO())));

        // Subscriptions#subscriptionsUuidPut
        wireMockServer.stubFor(put(urlEqualTo("/subscriptions/" + uuid))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer " + accessToken))
                .willReturn(aResponse()
                        .withStatus(200)));

        wireMockServer.start();
        return wireMockServer;
    }


    private static String getToken() {
        KeycloakTokenDTO tokenDto = new KeycloakTokenDTO();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setTokenType("Bearer");

        try {
            return jsonMapper.writeValueAsString(tokenDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getNumberOfServices() {
        LongDTO longDTO = new LongDTO();
        longDTO.setValue(numberOfServices);

        try {
            return jsonMapper.writeValueAsString(longDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResponseDTOServicesDTO() {
        ResponseDTOServiceDTO result = new ResponseDTOServiceDTO();
        try {
            return jsonMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getAuthorityDetailsDTO() {
        AuthorityDetailsDTO authorityDetailsDTO = new AuthorityDetailsDTO();

        try {
            return jsonMapper.writeValueAsString(authorityDetailsDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getNodeConfigurationDTO() {
        NodeConfigurationDTO nodeConfigurationDTO = new NodeConfigurationDTO();

        try {
            return jsonMapper.writeValueAsString(nodeConfigurationDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getContactPersonDTO() {
        try {
            return jsonMapper.writeValueAsString(contactPersonDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getParticipantDTO() {
        try {
            return jsonMapper.writeValueAsString(participantDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResponseDTOSubscriptionDTO() {
        ResponseDTOSubscriptionDTO result = new ResponseDTOSubscriptionDTO();
        try {
            return jsonMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
