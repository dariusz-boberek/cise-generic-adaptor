package eu.cise.adaptor.core.stepdefs;


import eu.cise.adaptor.core.FunctionalAppContext;
import eu.cise.adaptor.core.Utils;
import eu.cise.adaptor.core.adapters.servicehandler.out.stub.MessageRepositoryPortAdapterStub;
import eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.UpdateLegacySystemResult;
import eu.cise.adaptor.core.servicehandler.service.DefaultServiceHandlerRoutingService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.cucumber.java8.En;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static eu.cise.adaptor.core.Constants.PERSISTENCE_TURNED_OFF;
import static eu.cise.adaptor.core.Constants.PERSISTENCE_TURNED_ON;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReceiveMessageStepsDefinition implements En {

    @Captor
    ArgumentCaptor<RegisteredMessage> registeredMessageArgumentCaptor;
    @Captor
    ArgumentCaptor<String> messageIdCaptor;

    @Captor
    ArgumentCaptor<String> contextIdCaptor;
    @Captor
    ArgumentCaptor<List<RegisteredMessage>> messagesHistoryCaptor;
    private XmlMapper xmlMapper;
    private SignatureService signatureService;
    private Properties signatureProperties;
    private FunctionalAppContext ctx;
    private SendToLegacySystemPort sendToLegacySystemPortMock;
    private SendToLegacySystemPort sendToLegacySystemAlternativePortMock;
    private IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort;
    private String message;
    private Acknowledgement ack;
    private boolean isPersistenceTurnedOn = false;
    private Optional<MessageRepositoryPort> messageRepositoryPort;

    private Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap;


    public ReceiveMessageStepsDefinition() {
        Before(() -> {
            MockitoAnnotations.openMocks(this); //required for captors to properly initialize
            xmlMapper = new DefaultXmlMapper();
            signatureProperties = new Properties();
            readSignatureProperties();
            signatureService = newSignatureService(xmlMapper)
                    .withKeyStoreName(signatureProperties.getProperty("signature.keystore.filename"))
                    .withKeyStorePassword(signatureProperties.getProperty("signature.keystore.password"))
                    .withPrivateKeyAlias(signatureProperties.getProperty("signature.privatekey.alias"))
                    .withPrivateKeyPassword(signatureProperties.getProperty("signature.privatekey.password"))
                    .build();
            sendToLegacySystemPortMock = mock(SendToLegacySystemPort.class);
            sendToLegacySystemAlternativePortMock = mock(SendToLegacySystemPort.class);
            sendToLegacySystemPortMap = new HashMap<>();

            // assign mock related to "risk" service type
            sendToLegacySystemPortMap.put("node04.risk.pull.provider", sendToLegacySystemPortMock);
            sendToLegacySystemPortMap.put("node03.risk.pull.consumer", sendToLegacySystemPortMock);
            sendToLegacySystemPortMap.put("node08.risk.push.consumer", sendToLegacySystemPortMock);
            sendToLegacySystemPortMap.put("node22.risk.subscribe.consumer", sendToLegacySystemPortMock);

            // assign mock related to "vessel" service type
            sendToLegacySystemPortMap.put("node02.vessel.pull.provider", sendToLegacySystemAlternativePortMock);
            sendToLegacySystemPortMap.put("node06.vessel.push.consumer", sendToLegacySystemAlternativePortMock);

            when(sendToLegacySystemPortMock.updateLegacySystem(
                    registeredMessageArgumentCaptor.capture(),
                    messageIdCaptor.capture(),
                    messagesHistoryCaptor.capture(),
                    contextIdCaptor.capture()
            )).thenReturn(new UpdateLegacySystemResult("any"));
        });

        // Scenario: The CISE Adaptor receives a CISE {messageType} Message and forwards information to the Legacy System.
        Given("a properly signed CISE {} Message represented by file {string}", (String messageType, String filePath) -> {
            initContext(PERSISTENCE_TURNED_OFF);
            message = Utils.readResource(filePath, ReceiveMessageStepsDefinition.class);
            incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        });

        Given("Adapter with persistence and a properly signed CISE {} Message represented by file {string}", (String messageType, String filePath) -> {
            initContext(PERSISTENCE_TURNED_ON);
            message = Utils.readResource(filePath, ReceiveMessageStepsDefinition.class);
            incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        });

        // Scenario: The CISE Adaptor accepts a CISE {messageType} Message and replies with an XML validation error.
        Given("an invalid XML representation of CISE {} Message represented by file {string}", (String messageType, String filePath) -> {
            initContext(PERSISTENCE_TURNED_OFF);
            message = Utils.readResource(filePath, ReceiveMessageStepsDefinition.class);
            incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        });

        // Scenario: The CISE Adaptor accepts a CISE {messageType} Message and replies with a signature error.
        Given("an improperly signed CISE {} Message represented by file {string}", (String messageType, String filePath) -> {
            initContext(PERSISTENCE_TURNED_OFF);
            message = Utils.readResource(filePath, ReceiveMessageStepsDefinition.class);
            incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        });

        When("the Adaptor receives the message", () -> {
            ack = incomingNodeToMessageHandlerPort.requestSyncAck(message);
        });

        Then("an Acknowledgement with SUCCESS Status is replied", () -> {
            assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
        });

        Then("information is forwarded to the Legacy System.", () -> {
            verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(any(), any(), any(), any());
        });

        Then("messageId, message is forwarded to the Legacy System.", () -> {
            verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(any(), any(), any(), any());

            RegisteredMessage capturedRegisteredMessage = registeredMessageArgumentCaptor.getValue();
            String capturedMessageId = messageIdCaptor.getValue();
            List<RegisteredMessage> capturedMessagesHistory = messagesHistoryCaptor.getValue();
            Message ciseMessage = xmlMapper.fromXML(message);

            assertFalse(isPersistenceTurnedOn);
            assertThat(capturedRegisteredMessage.getMessageId()).isEqualTo(ciseMessage.getMessageID());
            assertThat(capturedMessageId).isEqualTo(ciseMessage.getMessageID());
            assertThat(capturedMessagesHistory)
                    .isNotNull()
                    .isEmpty();
        });

        Then("contextId with value {string} is forwarded to the Legacy System.", (String expectedContextId) -> {
            // risk LS called
            verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(any(), any(), any(), any());
            // vessel LS not called
            verify(sendToLegacySystemAlternativePortMock, times(0)).updateLegacySystem(any(), any(), any(), any());
            assertTrue(isPersistenceTurnedOn);

            RegisteredMessage capturedRegisteredMessage = registeredMessageArgumentCaptor.getValue();
            String capturedMessageId = messageIdCaptor.getValue();
            Message ciseMessage = xmlMapper.fromXML(message);
            assertThat(capturedRegisteredMessage.getContextId()).isEqualTo(expectedContextId);
            assertThat(capturedRegisteredMessage.getCiseMessage().getContextID()).isEqualTo(expectedContextId);
        });


        Then("messageId, message, message history is forwarded to the Legacy System.", () -> {
            verify(sendToLegacySystemAlternativePortMock, times(0)).updateLegacySystem(any(), any(), any(), any());
            verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(any(), any(), any(), any());
            RegisteredMessage capturedRegisteredMessage = registeredMessageArgumentCaptor.getValue();
            String capturedMessageId = messageIdCaptor.getValue();
            List<RegisteredMessage> capturedMessagesHistory = messagesHistoryCaptor.getValue();
            Message ciseMessage = xmlMapper.fromXML(message);

            assertTrue(isPersistenceTurnedOn);
            assertThat(capturedRegisteredMessage.getMessageId()).isEqualTo(ciseMessage.getMessageID());
            assertThat(capturedMessageId).isEqualTo(ciseMessage.getMessageID());
            assertThat(capturedMessagesHistory)
                    .isNotNull()
                    .isEmpty();
            assertThat(getMessageRepositoryPort().getMessagesHistoryByCorrelationId(ciseMessage.getCorrelationID()))
                    .isNotNull()
                    .isNotEmpty();
        });

        Then("message is forwarded to alternative Legacy System", () -> {
            verify(sendToLegacySystemPortMock, times(0)).updateLegacySystem(any(), any(), any(), any());
            verify(sendToLegacySystemAlternativePortMock, times(1)).updateLegacySystem(any(), any(), any(), any());
        });

        Then("no information is forwarded to the Legacy System.", () -> {
            verify(sendToLegacySystemPortMock, times(0)).updateLegacySystem(any(), any(), any(), any());
        });

        Then("an Acknowledgement with AUTHENTICATION_ERROR Status is replied.", () -> {
            assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.AUTHENTICATION_ERROR);
        });

        Then("an Acknowledgement with BAD_REQUEST Status is replied.", () -> {
            assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
        });

        Then("message is being saved", () -> {
            assertTrue(isPersistenceTurnedOn);
            Message ciseMessage = xmlMapper.fromXML(message);
            Optional<Message> maybeMessage = getMessageRepositoryPort().getByMessageId(ciseMessage.getMessageID());
            assertThat(maybeMessage)
                    .isNotEmpty()
                    .isNotNull();
            assertThat(getMessageRepositoryPort().getMessagesHistoryByCorrelationId(ciseMessage.getCorrelationID()))
                    .isNotNull()
                    .isNotEmpty();
        });
    }

    //INFO,@synchro0810: in similar fashion add 2 cases - we assume on one hand no repo is available and on the other it can be used.
    private void initContext(boolean isPersistenceTurnedOn) {
        if (isPersistenceTurnedOn) {
            this.isPersistenceTurnedOn = true;
            this.messageRepositoryPort = Optional.of(new MessageRepositoryPortAdapterStub());
            ctx = FunctionalAppContext
                    .builder()
                    .setSignatureService(signatureService)
                    .setSendToLegacySystemPorts(sendToLegacySystemPortMap)
                    .setServiceHandlerRoutingService(new DefaultServiceHandlerRoutingService(
                            sendToLegacySystemPortMap,
                            messageRepositoryPort.get()))
                    .buildWithFallbackToDefault();
        } else {
            ctx = FunctionalAppContext
                    .builder()
                    .setSignatureService(signatureService)
                    .setSendToLegacySystemPorts(sendToLegacySystemPortMap)
                    .buildWithFallbackToDefault();
        }
    }

    private void readSignatureProperties() throws IOException {
        URI resourceURI = Utils.getResourceURI("signature.properties", ReceiveMessageStepsDefinition.class);
        try (FileInputStream in = new FileInputStream(new File(resourceURI))) {
            signatureProperties.load(in);
        }
    }

    private MessageRepositoryPort getMessageRepositoryPort() {
        return messageRepositoryPort.orElseThrow(() ->
                new RuntimeException("messageRepositoryPort is not initialized. Ensure you're in a flow where the context is initialized with initContext(PERSISTENCE_TURNED_ON) in the Given clause"));
    }
}

