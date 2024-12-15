package eu.cise.quarkus;

import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.quarkus.servicehandler.MessageAuxiliaryOperationsImpl;
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
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class MessageResourceSendToLegacySystemMockTest {

    public static final String APPLICATION_XML = "application/xml";
    public static final String REST_API_MESSAGES = "/rest/api/messages";

    @Inject
    MessageRepositoryPort messageRepositoryInternalDBAdaptor;
    @Mock
    SendToLegacySystemPort sendToLegacySystemPortMock = Mockito.mock(SendToLegacySystemPort.class);
    @Inject
    MessageAuxiliaryOperationsImpl messageAuxiliaryOperations;

    @InjectMock
    private Map<String, SendToLegacySystemPort> sendToLegacySystemPortMapMock;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-file")
    String serverKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-password")
    String serverKeystorePassword;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-file")
    String clientKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-password")
    String clientKeystorePassword;

    @BeforeEach
    @Transactional
    public void setup() {
        messageAuxiliaryOperations.deleteAllMessages();
        when(sendToLegacySystemPortMapMock.get(anyString())).thenReturn(sendToLegacySystemPortMock);
        when(sendToLegacySystemPortMapMock.keySet()).thenReturn(TestSetupData.serviceIdsStub);
    }

    @Test
    void it_accepts_a_valid_PullRequest_message_then_store_it_in_db_then_verify_outgoing_port_was_called() throws InterruptedException {

        // GIVEN
        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml");
        String messageId = "f28e1703-4c0d-48ce-aba5-e187a3321333";
        Assertions.assertNotNull(sendToLegacySystemPortMock);

        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(sendToLegacySystemPortMock).updateLegacySystem(any(), any(), any(), any());

        // WHEN
        Response response = postMessage(xmlMessage);

        // THEN
        boolean isUpdated = latch.await(10, TimeUnit.SECONDS);
        assertThat(isUpdated).isTrue();
        assertThat(messageRepositoryInternalDBAdaptor).isNotNull();
        assertThat(messageRepositoryInternalDBAdaptor.getByMessageId(messageId))
                .isPresent()
                .hasValueSatisfying(message -> assertThat(message.getMessageID()).isEqualTo(messageId));
        assertResponseAndAck(response, extractAcknowledgementFromResponse(response), 202, messageId, AcknowledgementType.SUCCESS);

        // THEN
        verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(any(), any(), any(), any());
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

    private void assertResponseAndAck(Response response, Acknowledgement responseAck, int expectedStatusCode, String expectedCorrelationId, AcknowledgementType expectedAckCode) {
        Assertions.assertEquals(expectedStatusCode, response.statusCode());
        Assertions.assertEquals(expectedCorrelationId, responseAck.getCorrelationID());
        Assertions.assertEquals(expectedAckCode, responseAck.getAckCode());
    }

}