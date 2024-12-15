package eu.cise.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.apache.http.NoHttpResponseException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.config;
import static io.restassured.config.SSLConfig.sslConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class HttpConfigurationTest {

    Logger logger = LoggerFactory.getLogger(HttpConfigurationTest.class);

    @ConfigProperty(name = "quarkus.http.insecure-requests")
    String insecureRequests;

    @ConfigProperty(name = "quarkus.http.port")
    int httpPort;

    @Test
    void it_test_http_insecure_request_set_to_disabled() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.config = config().sslConfig(sslConfig().relaxedHTTPSValidation());
        RestAssured.get("/q/health").then().statusCode(200);
        assertEquals("disabled", insecureRequests, "HTTP should be disabled, but it's enabled.");
    }


    @Test
    void it_test_http_call_throws_error() {
        RestAssured.baseURI = "http://localhost";
        logger.debug("HTTP Port: {}", httpPort);
        assertThrows(NoHttpResponseException.class, () -> {
            RestAssured.get("/q/health");
        }, "Expected NoHttpResponseException when `quarkus.http.insecure-requests=disabled`");
    }

}
