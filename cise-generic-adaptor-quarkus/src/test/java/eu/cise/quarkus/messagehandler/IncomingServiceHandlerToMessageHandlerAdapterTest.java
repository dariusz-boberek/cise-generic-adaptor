package eu.cise.quarkus.messagehandler;

import com.github.tomakehurst.wiremock.WireMockServer;
import eu.cise.adaptor.core.common.utils.AcknowledgementUtils;
import eu.cise.adaptor.quarkus.messagehandler.adapter.in.IncomingServiceHandlerToMessageHandlerAdapter;
import eu.cise.quarkus.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;

@QuarkusTest
class IncomingServiceHandlerToMessageHandlerAdapterTest {

    @Inject
    EventBus eventBus;

    @InjectSpy
    IncomingServiceHandlerToMessageHandlerAdapter incomingServiceHandlerToMessageHandlerAdapter;

    private XmlMapper xmlMapper = new DefaultXmlMapper();
    WireMockServer wireMockServer;

    @AfterEach
    public void cleanup() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void it_receives_a_message_event_from_legacy_system_forwards_to_cisenode_then_replies_with_success_ack() throws InterruptedException {
        // GIVEN
        createAndStartWiremockServer();

        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        Message incomingFromServiceHandlerMessage = xmlMapper.fromXML(xmlMessage);
        Message[] incomingFromServiceHandlerMessageHolder = new Message[1];
        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            incomingFromServiceHandlerMessageHolder[0] = invocation.getArgument(0);
            latch.countDown();
            return invocation.callRealMethod();
        }).when(incomingServiceHandlerToMessageHandlerAdapter).incomingMessageHandler(any(Message.class));

        // WHEN
        Uni<Acknowledgement> ackReceiver = eventBus.<Acknowledgement>request("incoming-from-legacy-system", incomingFromServiceHandlerMessage)
                .onItem()
                .transform(acknowledgementMessage -> acknowledgementMessage.body());

        // THEN
        Acknowledgement ackResponse = ackReceiver.await().indefinitely();
        boolean isUpdated = latch.await(10, TimeUnit.SECONDS);
        Message processedMessage = incomingFromServiceHandlerMessageHolder[0];

        assertThat(isUpdated).isTrue();
        assertThat(ackResponse).isNotNull();
        assertThat(ackResponse.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);

        assertThat(processedMessage).isNotNull();
        // the messages should not be equal but only because of the creationDate
        assertMessages(false, incomingFromServiceHandlerMessage, processedMessage);
        // change the creationDate to be equal and retest
        incomingFromServiceHandlerMessage.setCreationDateTime(processedMessage.getCreationDateTime());
        assertMessages(true, incomingFromServiceHandlerMessage, processedMessage);
    }

    //TODO: we need to work on the test below to make sure that any exception happening when sending the message to the CISE Node is correctly reflected on the generated Ack
    @Test
    @Disabled
    void it_receives_a_message_event_from_legacy_system_handles_exception_to_cisenode_then_replies_with_error_ack() throws InterruptedException {

        // GIVEN
        XmlMapper xmlMapper = new DefaultXmlMapper();
        String xmlMessage = ResourcesUtils.getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        Message incomingFromServiceHandlerMessage = xmlMapper.fromXML(xmlMessage);
        Message[] processedMessageHolder = new Message[1];
        CountDownLatch latch = new CountDownLatch(1);


        doAnswer(invocation -> {
            processedMessageHolder[0] = invocation.getArgument(0);
            latch.countDown();
            return AcknowledgementUtils.buildAckError(new RuntimeException("Test Runtime Exception"), incomingFromServiceHandlerMessage);
        }).when(incomingServiceHandlerToMessageHandlerAdapter).sendMessage(any(Message.class));

        // WHEN
        Uni<Acknowledgement> ackReceiver = eventBus.<Acknowledgement>request("incoming-from-legacy-system", incomingFromServiceHandlerMessage)
                .onItem()
                .transform(acknowledgementMessage -> acknowledgementMessage.body());

        // THEN
        Acknowledgement ackResponse = ackReceiver.await().indefinitely();
        boolean isUpdated = latch.await(10, TimeUnit.SECONDS);
        Message processedMessage = processedMessageHolder[0];

        assertThat(isUpdated).isTrue();
        assertThat(ackResponse).isNotNull();
        assertThat(ackResponse.getAckCode()).isEqualTo(AcknowledgementType.SERVER_ERROR);

        assertThat(processedMessage).isNotNull();
        String xmlProcessedOriginalMessage = xmlMapper.toXML(xmlMapper.fromXML(xmlMessage));
        String xmlProcessedMessage = xmlMapper.toXML(processedMessage);
        assertThat(xmlProcessedOriginalMessage).isEqualTo(xmlProcessedMessage);
    }

    private void assertMessages(boolean expectedEqual, Message firstMessage, Message secondMessage) {
        String firstMessageXML = xmlMapper.toXML(firstMessage);
        String xmlProcessedMessage = xmlMapper.toXML(secondMessage);
        if (expectedEqual) {
            assertThat(firstMessageXML).isEqualTo(xmlProcessedMessage);
        } else {
            assertThat(firstMessageXML).isNotEqualTo(xmlProcessedMessage);
        }

    }

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-file")
    String serverKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.key-store-password")
    String serverKeystorePassword;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-file")
    String clientKeystore;

    @ConfigProperty(name = "quarkus.http.ssl.certificate.trust-store-password")
    String clientKeystorePassword;


    private WireMockServer createAndStartWiremockServer() {

        Path keystorePath = null;
        Path trustStorePath = null;

        try {
            // client keystore generated using the following command:
            // keytool -genkeypair -storepass password -keyalg RSA -keysize 2048 -dname "CN=client" -alias client -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore client.keystore
            // server keystore generated using the following command:
            // keytool -genkeypair -storepass password -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore server.keystore
            keystorePath = Paths.get(IncomingServiceHandlerToMessageHandlerAdapterTest.class.getClassLoader().getResource(serverKeystore).toURI());
            trustStorePath = Paths.get(IncomingServiceHandlerToMessageHandlerAdapterTest.class.getClassLoader().getResource(clientKeystore).toURI());
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
                .trustStorePassword(clientKeystorePassword));

        wireMockServer.stubFor(post(urlEqualTo("/messages"))
                .withHeader("Accept", equalTo("application/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/xml")
                        .withBody(syncAck())));
        wireMockServer.start();
        return wireMockServer;
    }





    private String syncAck() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns4:Acknowledgement xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
                "    <CorrelationID>d8a003e2-9b0a-4b09-9d2b-94ed87f92c1f_d62b0e1f-a9d2-4333-92ef-0e4aaaf012f2</CorrelationID>\n" +
                "    <CreationDateTime>2019-08-22T13:58:43.923Z</CreationDateTime>\n" +
                "    <MessageID>01d19315-de7e-4709-840c-3078a92fe1ca</MessageID>\n" +
                "    <Priority>High</Priority>\n" +
                "    <RequiresAck>false</RequiresAck>\n" +
                "    <AckCode>Success</AckCode>\n" +
                "    <AckDetail>Message delivered</AckDetail>\n" +
                "</ns4:Acknowledgement>";
    }

}