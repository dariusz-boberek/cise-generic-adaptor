package eu.cise.quarkus;


import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.quarkus.messagehandler.adapter.in.IncomingNodeToMessageHandlerAdapter;
import eu.cise.quarkus.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;


@QuarkusTest
class MessageResourceNodeToMessageHandlerMockTest {

    public static final String APPLICATION_XML = "application/xml";
    public static final String REST_API_MESSAGES = "/rest/api/messages";
    public static final int SERVER_ERROR__WITH_HTTP_ERROR_CODE_500 = 500;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-file")
    String serverKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-password")
    String serverKeystorePassword;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-file")
    String clientKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-password")
    String clientKeystorePassword;

    @InjectMock
    IncomingNodeToMessageHandlerAdapter incomingNodeToMessageHandlerAdapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void after() {
        Mockito.reset(incomingNodeToMessageHandlerAdapter);
    }

    /*
        In this test we are checking the internal server error simulating an issue in the internal bus communication
     */
    @Test
    public void it_refuses_a_message_with_an_http_server_error() {
        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        Mockito.when(incomingNodeToMessageHandlerAdapter.incomingMessageHandler(anyString())).thenThrow(new CiseAdaptorRuntimeException("123456789", AcknowledgementType.SERVER_ERROR));

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        Assertions.assertEquals(SERVER_ERROR__WITH_HTTP_ERROR_CODE_500, response.statusCode());
        assertThat(response.getBody().asString()).isNotEmpty().contains("io.vertx.core.eventbus.Message.fail(").contains("123456789");
    }


    private Response postMessage(String xmlMessage) {
        return given()
                .keyStore(clientKeystore,clientKeystorePassword)
                .trustStore(serverKeystore,serverKeystorePassword)
                .header("Content-type", APPLICATION_XML).and().body(xmlMessage).when().post(REST_API_MESSAGES).then().extract().response();
    }


}