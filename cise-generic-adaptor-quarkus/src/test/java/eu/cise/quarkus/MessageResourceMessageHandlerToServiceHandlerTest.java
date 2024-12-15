package eu.cise.quarkus;


import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.quarkus.servicehandler.adapter.in.IncomingMessageHandlerToServiceHandlerAdapter;
import eu.cise.quarkus.utils.ResourcesUtils;
import eu.cise.quarkus.utils.TestSetupData;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.eucise.xml.DefaultXmlMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@QuarkusTest
class MessageResourceMessageHandlerToServiceHandlerTest {

    public static final String APPLICATION_XML = "application/xml";
    public static final String REST_API_MESSAGES = "/rest/api/messages";
    public static final String CORRELATION_ID = "f16467a3-bd06-4166-ab04-71aee6a5f6b2";

    public static final int MSG_DELIVERD_WITH_HTTP_ERROR_CODE_200 = 200;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-file")
    String serverKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-password")
    String serverKeystorePassword;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-file")
    String clientKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-password")
    String clientKeystorePassword;




    @InjectMock
    Map<String, SendToLegacySystemPort> sendToLegacySystemPortMapMock;

    @InjectMock
    private IncomingMessageHandlerToServiceHandlerAdapter incomingMessageHandlerToServiceHandlerAdapter;

    @BeforeEach
    void setup() {
        when(sendToLegacySystemPortMapMock.keySet()).thenReturn(TestSetupData.serviceIdsStub);
    }

    @Test
    void it_accepts_a_valid_Push_message_and_triggers_processedMessagesHandler() throws InterruptedException {
        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        setupMockForProcessedMessagesHandler();

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        Acknowledgement responseAck = extractAcknowledgementFromResponse(response);
        assertResponseAndAck(response, responseAck, 202, CORRELATION_ID, AcknowledgementType.SUCCESS);
    }

    @Test
    void it_accepts_a_valid_PullRequest_message() throws InterruptedException {
        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml");
        setupMockForProcessedMessagesHandler();

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        Acknowledgement responseAck = extractAcknowledgementFromResponse(response);
        assertResponseAndAck(response, responseAck, 202, "f28e1703-4c0d-48ce-aba5-e187a3321333", AcknowledgementType.SUCCESS);
    }

    @Test
    void it_accepts_a_valid_message_for_KML_translation() throws InterruptedException {
        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/Push_vesselPushConsumer_manyVessels.xml");
        setupMockForProcessedMessagesHandler();

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        Acknowledgement responseAck = extractAcknowledgementFromResponse(response);
        assertResponseAndAck(response, responseAck, 202, "6839402c-5d5e-4028-a8cd-b41c14051508", AcknowledgementType.SUCCESS);
    }

    @Test
    public void it_refuses_an_invalid_message() {
        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/incorrect/Push_vesselPushConsumer_notValid.xml");

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        Assertions.assertEquals(MSG_DELIVERD_WITH_HTTP_ERROR_CODE_200, response.statusCode());
        Acknowledgement responseAck = extractAcknowledgementFromResponse(response);
        Assertions.assertEquals(CORRELATION_ID, responseAck.getCorrelationID());
        Assertions.assertEquals(AcknowledgementType.BAD_REQUEST, responseAck.getAckCode());
    }

    @Test
    public void it_refuses_a_message_with_wrong_signature() {
        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/incorrect/Push_riskPushConsumer_Signature_KO.xml");

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        Assertions.assertEquals(MSG_DELIVERD_WITH_HTTP_ERROR_CODE_200, response.statusCode());
        Acknowledgement responseAck = extractAcknowledgementFromResponse(response);
        Assertions.assertEquals(CORRELATION_ID, responseAck.getCorrelationID());
        Assertions.assertEquals(AcknowledgementType.AUTHENTICATION_ERROR, responseAck.getAckCode());
    }

    private Response postMessage(String xmlMessage) {
        return given()
                .keyStore(clientKeystore,clientKeystorePassword)
                .trustStore(serverKeystore,serverKeystorePassword)
                .header("Content-type", APPLICATION_XML).and().body(xmlMessage).when().post(REST_API_MESSAGES).then().extract().response();
    }

    private Acknowledgement extractAcknowledgementFromResponse(Response response) {
        response.getBody().prettyPrint();
        String responseString = response.getBody().asString();
        return new DefaultXmlMapper().fromXML(responseString);
    }

    private void setupMockForProcessedMessagesHandler() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(incomingMessageHandlerToServiceHandlerAdapter).processedMessagesHandler(any());
        latch.await(2, TimeUnit.SECONDS);
    }

    private void assertResponseAndAck(Response response, Acknowledgement responseAck, int expectedStatusCode, String expectedCorrelationId, AcknowledgementType expectedAckCode) {
        Assertions.assertEquals(expectedStatusCode, response.statusCode());
        Assertions.assertEquals(expectedCorrelationId, responseAck.getCorrelationID());
        Assertions.assertEquals(expectedAckCode, responseAck.getAckCode());
    }

}