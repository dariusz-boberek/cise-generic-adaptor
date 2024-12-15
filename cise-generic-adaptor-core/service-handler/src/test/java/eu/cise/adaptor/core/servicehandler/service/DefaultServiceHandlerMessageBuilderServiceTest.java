package eu.cise.adaptor.core.servicehandler.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.OutgoingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.PullResponse;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.eucise.helpers.PullRequestBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.cise.adaptor.core.servicehandler.domain.MessageProcessAction.SENT;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.PULL;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.PUSH;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.SUBSCRIBE;
import static eu.cise.servicemodel.v1.service.ServiceRoleType.CONSUMER;
import static eu.cise.servicemodel.v1.service.ServiceRoleType.PROVIDER;
import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static eu.eucise.helpers.ServiceProfileBuilder.newProfile;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DefaultServiceHandlerMessageBuilderServiceTest {

    private OutgoingServiceHandlerToMessageHandlerPort outgoingServiceHandlerToMessageHandlerPortMock;
    private ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService;
    private MessageRepositoryPort messageRepositoryPortMock;
    private XmlMapper messageXmlMapper;
    private XmlMapper payloadXmlMapper;

    @BeforeEach
    void setUp() {
        messageXmlMapper = new DefaultXmlMapper();
        payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        outgoingServiceHandlerToMessageHandlerPortMock = mock(OutgoingServiceHandlerToMessageHandlerPort.class);
        messageRepositoryPortMock = mock(MessageRepositoryPort.class);
        serviceHandlerMessageBuilderService = new DefaultServiceHandlerMessageBuilderService(outgoingServiceHandlerToMessageHandlerPortMock, messageRepositoryPortMock);
    }

    @Test
    void it_creates_pull_response_and_forwards_to_cise_when_reference_message_is_pull_request() throws CiseAdaptorValidationException {
        // Given
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullProvider_PullResponse_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class);
        CoreEntityPayload payload = payloadXmlMapper.fromXML(payloadXML);
        Message referenceMessage = messageXmlMapper.fromXML(ResourcesUtils.readResource("PullRequest_riskPullProvider_Signature_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class));
        List<Service> recipients = asList(referenceMessage.getSender());
        Acknowledgement mockAcknowledgement = createSuccessAcknowledgement();
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PULL);
        currentService.setServiceID("node04.risk.pull.provider");
        currentService.setServiceType(ServiceType.RISK_SERVICE);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(mockAcknowledgement);
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .referenceMessage(referenceMessage)
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);


        // Then
        assertMessageForwardedSuccessfully(result, payload);
    }


    @Test
    void it_constructs_and_sends_push_message_to_one_recipient() throws CiseAdaptorValidationException {
        // Given
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPushProvider_Push_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class);
        CoreEntityPayload payload = payloadXmlMapper.fromXML(payloadXML);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);
        Service recipientService = createVesselServiceWithRoleAndOperation(CONSUMER, PUSH);
        recipientService.setServiceID("testServiceID");
        List<Service> recipients = singletonList(recipientService);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage.getPayload()).isEqualTo(payload);
        assertThat(capturedMessage.getRecipient().getServiceID()).isEqualTo("testServiceID");
        verify(messageRepositoryPortMock, times(1)).save(any());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }

    @Test
    void it_creates_push_messages_for_all_recipients_and_forwards_them_to_cise() throws CiseAdaptorValidationException {
        // Given
        String expectedContextId = "contextId-123";
        List<Service> recipients = asList(createVesselServiceWithRoleAndOperation(CONSUMER, PUSH), createVesselServiceWithRoleAndOperation(CONSUMER, PUSH));
        recipients.get(0).setServiceID("1");
        recipients.get(1).setServiceID("2");
        CoreEntityPayload payloadMock = mock(CoreEntityPayload.class);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);
        Acknowledgement mockAcknowledgement = createSuccessAcknowledgement();
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(mockAcknowledgement);
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payloadMock)
                .currentService(currentService)
                .recipients(recipients)
                .contextId(expectedContextId)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(recipients.size())).forwardMessageToMessageHandler(messageCaptor.capture());
        List<Message> capturedMessages = messageCaptor.getAllValues();
        for (Message capturedMessage : capturedMessages) {
            assertThat(capturedMessage.getContextID()).isEqualTo(expectedContextId);
            assertThat(capturedMessage.getPayload()).isEqualTo(payloadMock);
        }
        verify(messageRepositoryPortMock, times(recipients.size())).save(any());
        for (Pair<RegisteredMessage, Acknowledgement> resultElem : result) {
            assertEquals(AcknowledgementType.SUCCESS, resultElem.getRight().getAckCode());
        }
    }

    @Test
    void it_logs_errors_for_unsuccessful_acknowledgements() throws CiseAdaptorValidationException {
        // Given
        List<Service> recipients = asList(
                createVesselServiceWithRoleAndOperation(CONSUMER, PUSH),
                createVesselServiceWithRoleAndOperation(CONSUMER, PUSH),
                createVesselServiceWithRoleAndOperation(CONSUMER, PUSH));
        recipients.get(0).setServiceID("1");
        recipients.get(1).setServiceID("2");
        recipients.get(2).setServiceID("3");
        CoreEntityPayload cisePayload = mock(CoreEntityPayload.class);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);
        Acknowledgement successAck = createSuccessAcknowledgement();
        Acknowledgement errorAck = new Acknowledgement();
        errorAck.setAckCode(AcknowledgementType.AUTHENTICATION_ERROR);
        errorAck.setAckDetail("Bad Service");
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any()))
                .thenReturn(successAck)
                .thenReturn(successAck)
                .thenReturn(errorAck);
        ch.qos.logback.classic.Logger loggerCaptor = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(DefaultServiceHandlerMessageBuilderService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        loggerCaptor.addAppender(listAppender);
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(cisePayload)
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(3)).forwardMessageToMessageHandler(any());
        assertEquals(AcknowledgementType.AUTHENTICATION_ERROR, result.get(2).getRight().getAckCode());
        // handleMessage: Failed Acknowledgement, code {} detail {} for messageId: {}
        ILoggingEvent specificErrorLog = findLogEventByMessageContains(listAppender.list,
                "handleMessage: Failed Acknowledgement, code " + errorAck.getAckCode());
        assertNotNull(specificErrorLog);
        assertThat(specificErrorLog.getLevel()).isEqualTo(Level.ERROR);
        listAppender.stop();
        listAppender.list.clear();
    }

    private ILoggingEvent findLogEventByMessageContains(List<ILoggingEvent> logs, String content) {
        return logs.stream()
                .filter(log -> log.getFormattedMessage().contains(content))
                .findFirst()
                .orElse(null);
    }


    @Test
    void it_persists_pull_response_to_internal_database() throws CiseAdaptorValidationException {

        // Given
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullProvider_PullResponse_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class);
        CoreEntityPayload payload = payloadXmlMapper.fromXML(payloadXML);
        Message referenceMessage = messageXmlMapper.fromXML(ResourcesUtils.readResource("PullRequest_riskPullProvider_Signature_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class));

        List<Service> recipients = singletonList(referenceMessage.getSender());
        Acknowledgement mockAcknowledgement = createSuccessAcknowledgement();
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PULL);
        currentService.setServiceID("node04.risk.pull.provider");
        currentService.setServiceType(ServiceType.RISK_SERVICE);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(mockAcknowledgement);
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .referenceMessage(referenceMessage)
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        assertMessageForwardedSuccessfully(result, payload);
        ArgumentCaptor<RegisteredMessage> registeredMessageArgumentCaptor = ArgumentCaptor.forClass(RegisteredMessage.class);
        verify(messageRepositoryPortMock, times(1)).save(registeredMessageArgumentCaptor.capture());
        RegisteredMessage capturedRegisteredMessage = registeredMessageArgumentCaptor.getValue();
        assertThat(capturedRegisteredMessage.getMessageProcessAction())
                .describedAs("MessageProcessAction of saved RegisteredMessage")
                .isEqualTo(SENT);
    }

    @Test
    void it_throws_error_when_no_message_has_been_created() {
        // Given

        CoreEntityPayload cisePayload = mock(CoreEntityPayload.class);
        Service currentService = mock(Service.class);

        assertThrows(CiseAdaptorValidationException.class, () -> MessageDataContext.getManager()
                .cisePayload(cisePayload)
                .currentService(currentService)
                .buildContext());
    }

    @Test
    void it_creates_subscribe_provider_message_and_forwards_to_cise() throws CiseAdaptorValidationException {
        // Given
        CoreEntityPayload payloadMock = mock(CoreEntityPayload.class);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE);
        Acknowledgement mockAcknowledgement = createSuccessAcknowledgement();
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(mockAcknowledgement);
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payloadMock)
                .currentService(currentService)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(any());
        verify(messageRepositoryPortMock, times(1)).save(any(RegisteredMessage.class));
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }

    @Test
    void it_returns_server_error_when_discovery_profiles_are_present_for_known_push_message() {
        // Given
        List<Service> recipients = new ArrayList<>();
        recipients.add(createVesselServiceWithRoleAndOperation(CONSUMER, PUSH));
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(mock(ServiceProfile.class));
        CoreEntityPayload payloadMock = mock(CoreEntityPayload.class);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);

        assertThrows(CiseAdaptorValidationException.class, () -> MessageDataContext.getManager()
                .cisePayload(payloadMock)
                .currentService(currentService)
                .recipients(recipients)
                .discoveryProfiles(discoveryProfiles)
                .buildContext());
    }


    @Test
    void it_constructs_and_sends_subscribe_consumer_message_to_one_recipient() throws CiseAdaptorValidationException {
        // Given
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, SUBSCRIBE);
        Service recipientService = createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE);
        recipientService.setServiceID("someServiceID");
        List<Service> recipients = singletonList(recipientService);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage.getRecipient().getServiceID()).isEqualTo("someServiceID");
        verify(messageRepositoryPortMock, times(1)).save(any());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }


    @Test
    void it_creates_subscribe_consumer_for_all_recipients_and_forwards_them_to_cise() throws CiseAdaptorValidationException {
        // Given
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, SUBSCRIBE);
        List<Service> recipients = asList(createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE), createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE));
        recipients.get(0).setServiceID("1");
        recipients.get(1).setServiceID("2");
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(recipients.size())).forwardMessageToMessageHandler(any());
        verify(messageRepositoryPortMock, times(recipients.size())).save(any());
        for (Pair<RegisteredMessage, Acknowledgement> pair : result) {
            assertEquals(AcknowledgementType.SUCCESS, pair.getRight().getAckCode());
        }
    }

    @Test
    void it_includes_contextId_in_one_push_message() throws CiseAdaptorValidationException {

        // Given
        String expectedContextId = "contextId-123";
        CoreEntityPayload payload = mock(CoreEntityPayload.class);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        Service recipien = createVesselServiceWithRoleAndOperation(CONSUMER, PUSH);
        recipien.setServiceID("1");
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(singletonList(recipien))
                .contextId(expectedContextId)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();

        assertThat(capturedMessage.getContextID()).isEqualTo(expectedContextId);
    }



    @Test
    void it_includes_contextId_in_many_push_messages() throws CiseAdaptorValidationException {

        // Given
        String expectedContextId = "contextId-123";
        CoreEntityPayload payload = mock(CoreEntityPayload.class);
        List<Service> recipients = asList(createVesselServiceWithRoleAndOperation(CONSUMER, PUSH), createVesselServiceWithRoleAndOperation(CONSUMER, PUSH));
        recipients.get(0).setServiceID("1");
        recipients.get(1).setServiceID("2");
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);

        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(recipients)
                .contextId(expectedContextId)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(recipients.size())).forwardMessageToMessageHandler(messageCaptor.capture());
        verify(messageRepositoryPortMock, times(recipients.size())).save(any());
        List<Message> capturedMessages = messageCaptor.getAllValues();
        capturedMessages.forEach(message -> assertThat(message.getContextID()).isEqualTo(expectedContextId));
        result.forEach(pair -> assertEquals(AcknowledgementType.SUCCESS, pair.getRight().getAckCode()));
    }



    @Test
    void it_includes_contextId_in_pullResponse() throws CiseAdaptorValidationException {
        // Given
        String expectedContextId = "contextId-123";
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PULL);
        currentService.setServiceID("0");
        CoreEntityPayload payload = payloadXmlMapper.fromXML(ResourcesUtils.readResource("Payload_For_vesselPullProvider_PullResponse_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class));

        Service pullRequestService = createVesselServiceWithRoleAndOperation(CONSUMER, PULL); //pullResponse recipient
        pullRequestService.setServiceID("1");

        PullRequest referencePullRequest = PullRequestBuilder.newPullRequest()
                .contextId(expectedContextId)
                .correlationId("correlationId-123")
                .sender(pullRequestService)
                .recipient(currentService) // currentService acting as recipient
                .build();

        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .referenceMessage(referencePullRequest)
                .contextId(expectedContextId) // Explicitly setting the contextId
                .buildContext();

        // When
        serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertEquals(expectedContextId, capturedMessage.getContextID());
    }

    @Test
    void it_includes_contextId_in_subscribeConsumer_when_single_recipient() throws CiseAdaptorValidationException {
        // Given
        String expectedContextId = "contextId-12";
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, SUBSCRIBE);
        Service recipientService = createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE);
        recipientService.setServiceID("someServiceID");
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(singletonList(recipientService))
                .contextId(expectedContextId)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertEquals(expectedContextId, capturedMessage.getContextID());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }

    @Test
    void it_includes_contextId_in_subscribeConsumer_when_many_recipients() throws CiseAdaptorValidationException {
        // Given
        String expectedContextId = "contextId-123";
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, SUBSCRIBE);
        List<Service> recipientServices = asList(createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE), createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE));
        recipientServices.get(0).setServiceID("0");
        recipientServices.get(1).setServiceID("1");
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(recipientServices)
                .contextId(expectedContextId)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(recipientServices.size())).forwardMessageToMessageHandler(messageCaptor.capture());
        List<Message> capturedMessages = messageCaptor.getAllValues();
        assertThat(capturedMessages)
                .extracting(Message::getContextID)
                .containsOnly(expectedContextId);
        result.forEach(pair -> assertEquals(AcknowledgementType.SUCCESS, pair.getRight().getAckCode()));
    }


    @Test
    void it_includes_contextId_in_subscribeProvider() throws CiseAdaptorValidationException {
        // Given
        String expectedContextId = "contextId-123";
        CoreEntityPayload payloadMock = mock(CoreEntityPayload.class);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, SUBSCRIBE);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payloadMock)
                .currentService(currentService)
                .contextId(expectedContextId)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertEquals(expectedContextId, capturedMessage.getContextID());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }

    @Test
    void it_constructs_and_sends_pull_request_message_to_one_recipient() throws CiseAdaptorValidationException {
        // Given
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullConsumer_PullRequest_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class);
        CoreEntityPayload payload = payloadXmlMapper.fromXML(payloadXML);
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, PULL);
        Service recipientService = createVesselServiceWithRoleAndOperation(PROVIDER, PULL);
        recipientService.setServiceID("testServiceID");
        List<Service> recipients = singletonList(recipientService);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage).isInstanceOf(PullRequest.class);
        assertThat(capturedMessage.getPayload()).isEqualTo(payload);
        assertThat(capturedMessage.getRecipient().getServiceID()).isEqualTo("testServiceID");
        verify(messageRepositoryPortMock, times(1)).save(any());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }


    // Unknonw patterns tests

    @Test
    void it_constructs_and_sends_push_unknown_message() throws CiseAdaptorValidationException {
        // Given
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPushProvider_Push_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class);
        CoreEntityPayload payload = payloadXmlMapper.fromXML(payloadXML);
        Service currentService = createVesselServiceWithRoleAndOperation(PROVIDER, PUSH);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        List<ServiceProfile> discoveryProfiles = singletonList(
                newProfile()
                        .serviceType(VESSEL_SERVICE)
                        .serviceOperation(PUSH)
                        .serviceRole(CONSUMER).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage).isInstanceOf(Push.class);
        assertThat(capturedMessage.getPayload()).isEqualTo(payload);
        assertThat(((Push)capturedMessage).getDiscoveryProfiles()).isNotEmpty();
        assertThat(((Push)capturedMessage).getDiscoveryProfiles().get(0)).isEqualTo(discoveryProfiles.get(0));
        assertThat(capturedMessage.getRecipient()).isNull();
        verify(messageRepositoryPortMock, times(1)).save(any());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }


    @Test
    void it_constructs_and_sends_pull_request_unknown_message() throws CiseAdaptorValidationException {
        // Given
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullConsumer_PullRequest_OK.xml", DefaultServiceHandlerMessageBuilderServiceTest.class);
        CoreEntityPayload payload = payloadXmlMapper.fromXML(payloadXML);
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, PULL);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        List<ServiceProfile> discoveryProfiles = singletonList(
                newProfile()
                        .serviceType(VESSEL_SERVICE)
                        .serviceOperation(PULL)
                        .serviceRole(PROVIDER).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage).isInstanceOf(PullRequest.class);
        assertThat(capturedMessage.getPayload()).isEqualTo(payload);
        assertThat(((PullRequest)capturedMessage).getDiscoveryProfiles()).isNotEmpty();
        assertThat(((PullRequest)capturedMessage).getDiscoveryProfiles().get(0)).isEqualTo(discoveryProfiles.get(0));
        assertThat(capturedMessage.getRecipient()).isNull();
        verify(messageRepositoryPortMock, times(1)).save(any());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }

    @Test
    void it_constructs_and_sends_pull_request_subscribe_unknown_message() throws CiseAdaptorValidationException {
        // Given
        Service currentService = createVesselServiceWithRoleAndOperation(CONSUMER, SUBSCRIBE);
        when(outgoingServiceHandlerToMessageHandlerPortMock.forwardMessageToMessageHandler(any())).thenReturn(createSuccessAcknowledgement());
        List<ServiceProfile> discoveryProfiles = singletonList(
                newProfile()
                        .serviceType(VESSEL_SERVICE)
                        .serviceOperation(SUBSCRIBE)
                        .serviceRole(PROVIDER).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();

        // When
        List<Pair<RegisteredMessage, Acknowledgement>> result = serviceHandlerMessageBuilderService.handleMessage(messageDataContext);

        // Then
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock, times(1)).forwardMessageToMessageHandler(messageCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage).isInstanceOf(PullRequest.class);
        assertThat(((PullRequest)capturedMessage).getDiscoveryProfiles()).isNotEmpty();
        assertThat(((PullRequest)capturedMessage).getDiscoveryProfiles().get(0)).isEqualTo(discoveryProfiles.get(0));
        assertThat(capturedMessage.getRecipient()).isNull();
        verify(messageRepositoryPortMock, times(1)).save(any());
        assertEquals(AcknowledgementType.SUCCESS, result.get(0).getRight().getAckCode());
    }



    private Acknowledgement createSuccessAcknowledgement() {
        Acknowledgement mockAcknowledgement = newAck()
                .id("currentMessageId")
                .correlationId("currentMessageId")
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .isRequiresAck(false)
                .ackCode(AcknowledgementType.SUCCESS)
                .ackDetail("")
                .build();
        mockAcknowledgement.setPayload(null);
        return mockAcknowledgement;
    }

    private static int serviceId = 0;
    private static Service createVesselServiceWithRoleAndOperation(ServiceRoleType serviceRoleType, ServiceOperationType serviceOperationType) {
        return newService()
                .serviceRole(serviceRoleType)
                .operation(serviceOperationType)
                .id(""+serviceId++)
                .type(VESSEL_SERVICE)
                .build();
    }

    private void assertMessageForwardedSuccessfully(List<Pair<RegisteredMessage, Acknowledgement>> resultList, CoreEntityPayload payload) {
        ArgumentCaptor<Message> messageCaptor = forClass(Message.class);
        verify(outgoingServiceHandlerToMessageHandlerPortMock).forwardMessageToMessageHandler(messageCaptor.capture());
        List<Message> allCapturedMessages = messageCaptor.getAllValues();
        for (int i = 0; i < resultList.size(); i++) {
            Pair<RegisteredMessage, Acknowledgement> resultPair = resultList.get(i);
            Acknowledgement ack = resultPair.getRight();
            Message ciseMessage = allCapturedMessages.get(i);

            assertEquals(AcknowledgementType.SUCCESS, ack.getAckCode());
            assertThat(ciseMessage)
                    .as("The sent message should be a PullResponse")
                    .isInstanceOf(PullResponse.class);
            assertThat(ciseMessage.getPayload())
                    .as("The payload should match")
                    .isEqualTo(payload);
        }
    }
}